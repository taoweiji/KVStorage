package com.taoweiji.kvstorage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.Modifier;

public class BuildJavaFile {

    public String build(String yamlFile, String packageName, String outFile) throws IOException {
        File file = new File(yamlFile);
        JSONObject json = YamlParserHelper.parser(file.getAbsolutePath());
        String fileName = file.getName().replace(".yaml", "");
        TypeSpec.Builder typeSpecBuilder = buildTypeSpec(fileName, json, true);
        JavaFile javaFile = JavaFile.builder(packageName, typeSpecBuilder.build()).build();
        javaFile.writeTo(new File(outFile));
        String humpName = StringUtils.lineToHump(fileName);
        return String.format("%s.%s(%s.java:0)", packageName, humpName, humpName);
    }

    ClassName KVStorage = ClassName.get("com.taoweiji.kvstorage", "KVStorage");
    //    ClassName FileMetadata = ClassName.get("com.taoweiji.kvstorage", "FileMetadata");
    ClassName Group = ClassName.get("com.taoweiji.kvstorage", "Group");
    ClassName GroupData = ClassName.get("com.taoweiji.kvstorage", "GroupData");
    ClassName ListMetadata = ClassName.get("com.taoweiji.kvstorage", "ListMetadata");
    ClassName SetMetadata = ClassName.get("com.taoweiji.kvstorage", "SetMetadata");
    ClassName Metadata = ClassName.get("com.taoweiji.kvstorage", "Metadata");
    ClassName ObjectMetadata = ClassName.get("com.taoweiji.kvstorage", "ObjectMetadata");
    ClassName ObjectListMetadata = ClassName.get("com.taoweiji.kvstorage", "ObjectListMetadata");

    public TypeSpec.Builder buildTypeSpec(String name, JSONObject object, boolean root) {
        Set<String> keys = object.keySet();
        MethodSpec.Builder constructor = MethodSpec.constructorBuilder().addParameter(GroupData, "data").addCode("super(data);\n");
        TypeSpec.Builder node = TypeSpec.classBuilder(StringUtils.lineToHump(name))
                .addModifiers(Modifier.PUBLIC).superclass(Group).addMethod(constructor.build());
        if (root) {
            node.addMethod(MethodSpec.methodBuilder("get")
                    .returns(ClassName.get("", StringUtils.lineToHump(name)))
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addCode("return new $T($T.getRootGroupData($S));\n", ClassName.get("", StringUtils.lineToHump(name)), KVStorage, name)
                    .build());
            // @SuppressWarnings("UnnecessaryLocalVariable")
            node.addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "UnnecessaryLocalVariable").build());
        } else {
            node.addModifiers(Modifier.STATIC);
        }
        CodeBlock.Builder clearCodeBlock = CodeBlock.builder();

        for (String key : keys) {
            if (key.startsWith(":")) {
                String readName = key.substring(1);
                JSONObject jsonObject = object.getJSONObject(key);
                if (jsonObject == null) {
                    throw new RuntimeException("yaml 异常，" + readName + " 没有填写数据类型");
                }
                TypeSpec.Builder typeSpecBuilder = buildTypeSpec(readName, (JSONObject) jsonObject, false);
                node.addType(typeSpecBuilder.build());
                ClassName className = ClassName.get("", StringUtils.lineToHump(name), StringUtils.lineToHump(readName));
                node.addMethod(MethodSpec.methodBuilder("get" + StringUtils.lineToHump(readName))
                        .returns(className)
                        .addStatement("return new $T(createGroupData($S))", className, readName)
                        .addModifiers(Modifier.PUBLIC)
                        .build());
                clearCodeBlock.addStatement("get$L().clear()", StringUtils.lineToHump(readName));
            } else {

                JSONObject item = object.getJSONObject(key);
                MethodSpec get = createGetMethodSpec(key, item);
                if (get != null) {
                    node.addMethod(get);
                }
                MethodSpec set = createSetMethodSpec(key, item);
                if (set != null) {
                    node.addMethod(set);
                }
            }
        }
        if (!clearCodeBlock.isEmpty()) {
            node.addMethod(MethodSpec.methodBuilder("clear")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addCode(clearCodeBlock.addStatement("super.clear()").build())
                    .build());
        }
        return node;
    }

    public MethodSpec createGetMethodSpec(String name, JSONObject item) {
        String humpName = StringUtils.lineToHump(name);
        String type = item.getString("type");
        boolean encrypt = item.getBooleanValue("encrypt");
        MethodSpec.Builder builder = MethodSpec.methodBuilder("get" + humpName);
        String codePart;
        if (encrypt) {
            codePart = "$T $L = get($S, true).";
        } else {
            codePart = "$T $L = get($S).";
        }
        TypeName listTypeArguments = null;
        switch (type) {
            case "int":
                int defInt = item.getIntValue("def");
                builder.returns(int.class).addStatement(codePart + "getInt($L)", int.class, name, name, defInt);
                break;
            case "long":
                long defLong = item.getLongValue("def");
                builder.returns(long.class).addStatement(codePart + "getLong($L)", long.class, name, name, defLong);
                break;
            case "float":
            case "double":
                float defFloat = item.getFloatValue("def");
                builder.returns(float.class).addStatement(codePart + "getFloat($Lf)", float.class, name, name, defFloat);
                break;
            case "bool":
            case "boolean":
                boolean defBool = item.getBooleanValue("def");
                builder = MethodSpec.methodBuilder("is" + humpName);
                builder.returns(boolean.class).addStatement(codePart + "getBool($L)", boolean.class, name, name, defBool);
                break;
            case "string":
                String defString = item.getString("def");
                builder.returns(String.class).addStatement(codePart + "getString($S)", String.class, name, name, defString);
                break;
            case "object":
                builder.returns(ObjectMetadata);
                builder.addStatement("$T $L = createObjectMetadata($S,$L)", ObjectMetadata, name, name, encrypt);
                break;
            case "list":
            case "list<object>":
                builder.returns(ObjectListMetadata);
                builder.addStatement("$T $L = createObjectListMetadata($S,$L)", ObjectListMetadata, name, name, encrypt);
                break;
            case "set":
            case "set<object>":
                listTypeArguments = ClassName.get("org.json", "JSONObject");
                break;
            case "list<int>":
            case "set<int>":
                listTypeArguments = ClassName.get(Integer.class);
                break;
            case "list<long>":
            case "set<long>":
                listTypeArguments = ClassName.get(Long.class);
                break;
            case "list<float>":
            case "set<float>":
                listTypeArguments = ClassName.get(Float.class);
                break;
            case "list<bool>":
            case "set<bool>":
                listTypeArguments = ClassName.get(Boolean.class);
                break;
            case "list<string>":
            case "set<string>":
                listTypeArguments = ClassName.get(String.class);
                break;

//            case "file":
//                builder.returns(FileMetadata).addCode("return new $T(get($S));\n", FileMetadata, name);
//                break;
            default:
                throw new RuntimeException("yaml 异常，" + name + " 类型错误(" + type + ")，仅支持 int、long、float、bool、string、object、list<int>、list<long>、list<float>、list<bool>、list<string>、list<object>、set<int>、set<long>、set<float>、set<bool>、set<string>、set<object>");
        }
        if (listTypeArguments != null) {
            if (type.startsWith("set")) {
                ParameterizedTypeName typeNames = ParameterizedTypeName.get(SetMetadata, listTypeArguments);
                builder.returns(typeNames);
                builder.addStatement("$T $L = createSetMetadata($S,$T.class,$L)", typeNames, name, name, listTypeArguments, encrypt);
            } else {
                ParameterizedTypeName typeNames = ParameterizedTypeName.get(ListMetadata, listTypeArguments);
                builder.returns(typeNames);
                builder.addStatement("$T $L = createListMetadata($S,$T.class,$L)", typeNames, name, name, listTypeArguments, encrypt);
            }
        }
        return builder.addStatement("return $L", name).addModifiers(Modifier.PUBLIC).build();
    }

    public MethodSpec createSetMethodSpec(String name, JSONObject item) {
        String humpName = StringUtils.lineToHump(name);
        String type = item.getString("type");
        Object def = item.get("def");
        boolean encrypt = item.getBooleanValue("encrypt");
        MethodSpec.Builder builder = MethodSpec.methodBuilder("set" + humpName);
        switch (type) {
            case "int":
                builder.addParameter(int.class, "value");
                break;
            case "long":
                builder.addParameter(long.class, "value");
                break;
            case "float":
            case "double":
                builder.addParameter(float.class, "value");
                break;
            case "bool":
            case "boolean":
                builder.addParameter(boolean.class, "value");
                break;
            case "string":
                builder.addParameter(String.class, "value");
                break;
            default:
                return null;
        }
        if (encrypt) {
            return builder.addModifiers(Modifier.PUBLIC).addStatement("get($S, true).set(value)", name).build();
        } else {
            return builder.addModifiers(Modifier.PUBLIC).addStatement("get($S).set(value)", name).build();
        }
    }
}
