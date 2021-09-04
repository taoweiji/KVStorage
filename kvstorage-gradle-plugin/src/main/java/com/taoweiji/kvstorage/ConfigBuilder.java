package com.taoweiji.kvstorage;

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

public class ConfigBuilder {

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


    ClassName ConfigGroup = ClassName.get("com.taoweiji.kvstorage", "ReadOnlyConfigGroup");
    ClassName Config = ClassName.get("com.taoweiji.kvstorage", "ReadOnlyConfig");

    public TypeSpec.Builder buildTypeSpec(String name, JSONObject object, boolean root) {
        Set<String> keys = object.keySet();
        MethodSpec.Builder constructor = MethodSpec.constructorBuilder().addParameter(String.class, "data").addCode("super(data);\n");
        TypeSpec.Builder node = TypeSpec.classBuilder(StringUtils.lineToHump(name))
                .addModifiers(Modifier.PUBLIC).superclass(ConfigGroup).addMethod(constructor.build());
        if (root) {
            node.addMethod(MethodSpec.methodBuilder("get")
                    .returns(ClassName.get("", StringUtils.lineToHump(name)))
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addCode("return new $T($S);\n", ClassName.get("", StringUtils.lineToHump(name)), name)
                    .build());
            // @SuppressWarnings("UnnecessaryLocalVariable")
            node.addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "UnnecessaryLocalVariable").build());
        } else {
            node.addModifiers(Modifier.STATIC);
        }

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
                        .addStatement("return new $T(createGroup($S))", className, readName)
                        .addModifiers(Modifier.PUBLIC)
                        .build());
            } else {
                JSONObject item = object.getJSONObject(key);
                MethodSpec get = createGetMethodSpec(key, item);
                if (get != null) {
                    node.addMethod(get);
                }
            }
        }
        return node;
    }

    public MethodSpec createGetMethodSpec(String name, JSONObject item) {
        String humpName = StringUtils.lineToHump(name);
        String type = item.getString("type");

        MethodSpec.Builder builder = MethodSpec.methodBuilder("get" + humpName);
        String codePart = "$T $L = get($S).";

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
//            case "object":
//                builder.returns(ObjectMetadata);
//                builder.addStatement("return createObjectMetadata($S,$L)", name, encrypt);
//                break;
//            case "list":
//            case "list<object>":
//                builder.returns(ObjectListMetadata);
//                builder.addStatement("return createObjectListMetadata($S,$L)", name, encrypt);
//                break;
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

            default:
                throw new RuntimeException("yaml 异常，" + name + " 类型错误(" + type + ")，仅支持 int、long、float、bool、string");
        }
//        if (listTypeArguments != null) {
//            if (type.startsWith("set")) {
//                builder.returns(ParameterizedTypeName.get(SetMetadata, listTypeArguments));
//                builder.addStatement("return createSetMetadata($S,$T.class,$L)", name, listTypeArguments, encrypt);
//            } else {
//                builder.returns(ParameterizedTypeName.get(ListMetadata, listTypeArguments));
//                builder.addStatement("return createListMetadata($S,$T.class,$L)", name, listTypeArguments, encrypt);
//            }
//        }
        return builder.addStatement("return $L", name).addModifiers(Modifier.PUBLIC).build();
    }

}
