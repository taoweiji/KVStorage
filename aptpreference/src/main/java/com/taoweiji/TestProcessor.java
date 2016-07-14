package com.taoweiji;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.taoweiji.apt.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class TestProcessor extends AbstractProcessor {

    private Elements elementUtils;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Test.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(Test.class);
//        System.out.printf("!!!!!!!");
//        for (Element element : set) {
//            if (element.getKind() != ElementKind.CLASS) {
//                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support class");
//            }
//            MethodSpec main = MethodSpec.methodBuilder("main")
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                    .returns(void.class)
//                    .addParameter(String[].class, "args")
//                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!!!!" + element.getSimpleName())
//                    .build();
//
//            TypeSpec helloWorld =
//                    TypeSpec.classBuilder("HelloWorld").addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(main).build();
//            JavaFile javaFile = JavaFile.builder("com.lighters.apt", helloWorld).build();
//
//            try {
//                javaFile.writeTo(processingEnv.getFiler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(com.taoweiji.apt.AptPreference.class);
        System.out.println("!!!!!生成AptPreference");
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            List<? extends Element> members = elementUtils.getAllMembers(typeElement);
            List<MethodSpec> methodSpecs = new ArrayList<>();
            for (Element item : members) {

                if (item instanceof ExecutableElement) {
                    ExecutableElement executableElement = (ExecutableElement) item;
                    String name = item.getSimpleName().toString();
                    if (name.equals("getClass")) {
                        continue;
                    }
                    if (name.startsWith("set")) {
                        System.out.println("设置：" + name);
                        VariableElement parameter = executableElement.getParameters().get(0);
                        TypeName type = TypeName.get(parameter.asType());
                        String modName;
                        if (type.equals(TypeName.BOOLEAN)) {
                            modName = "putBoolean";
                        } else if (type.equals(TypeName.INT)) {
                            modName = "putInt";
                        } else if (type.equals(TypeName.DOUBLE)) {
                            modName = "putDouble";
                        } else if (type.equals(TypeName.FLOAT)) {
                            modName = "putFloat";
                        } else if (type.equals(TypeName.LONG)) {
                            modName = "putLong";
                        } else {
                            modName = "putString";
                        }

                        MethodSpec setMethod = MethodSpec.overriding(executableElement)
                                .addStatement(String.format("mEdit.%s(\"%s\", %s).apply()",modName, parameter, parameter)).build();
                        methodSpecs.add(setMethod);
                    } else if (name.startsWith("get") || name.startsWith("is")) {
                        System.out.println("获取：" + name);
                        TypeName type = TypeName.get(executableElement.getReturnType());
                        String modName;
                        if (type.equals(TypeName.BOOLEAN)) {
                            modName = "getBoolean";
                        } else if (type.equals(TypeName.INT)) {
                            modName = "getInt";
                        } else if (type.equals(TypeName.FLOAT)) {
                            modName = "getFloat";
                        } else if (type.equals(TypeName.LONG)) {
                            modName = "getLong";
                        } else {
                            modName = "getString";
                        }
                        String simplename = name.replaceFirst("get|is","");
                        simplename = simplename.substring(0, 1).toLowerCase() + simplename.substring(1);


                        MethodSpec setMethod = MethodSpec.overriding(executableElement)
                                .addStatement(String.format("return mPreferences.%s(\"%s\", super.%s())",modName,simplename,name))
                                .build();
                        methodSpecs.add(setMethod);
                    }

                }
            }
//
//            System.out.println(getPackageName(typeElement));
//            if (element.getKind() != ElementKind.CLASS) {
//                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "only support class");
//            }
            MethodSpec main = MethodSpec.methodBuilder("get")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(TypeName.get(typeElement.asType()))
                    .addParameter(ClassName.get("android.content", "Context"), "context")
                    .addParameter(String.class, "name")
                    .addStatement("if (sMap.containsKey(name)) {\n" +
                            "            return (Settings) sMap.get(name);\n" +
                            "        }\n" +
                            "        synchronized (sMap) {\n" +
                            "            if (!sMap.containsKey(name)) {\n" +
                            "                SettingsPreference2 sharedPresUtil = new SettingsPreference2(context, name);\n" +
                            "                sMap.put(name, sharedPresUtil);\n" +
                            "            }\n" +
                            "        }\n" +
                            "        return (Settings) sMap.get(name)")
                    .build();


            FieldSpec fieldSpec = FieldSpec.builder(Map.class, "sMap", Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                    .initializer("new android.util.ArrayMap<String, Settings>()")
                    .build();
            TypeSpec typeSpec = TypeSpec.classBuilder(element.getSimpleName() + "Preference")
                    .superclass(TypeName.get(typeElement.asType()))
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethods(methodSpecs)
                    .addMethod(main)
                    .addField(ClassName.get("android.content", "SharedPreferences", "Editor"), "mEdit")
                    .addField(ClassName.get("android.content", "SharedPreferences"), "mPreferences")
                    .addField(fieldSpec)
                    .build();
            JavaFile javaFile = JavaFile.builder(getPackageName(typeElement), typeSpec).build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }
}