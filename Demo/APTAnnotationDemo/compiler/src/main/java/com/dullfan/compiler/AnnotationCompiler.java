package com.dullfan.compiler;

import com.dullfan.annotations.AptAnnotation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;


/**
 * 注解处理器
 */
@SupportedOptions("MODULE_NAME")
@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add("com.dullfan.annotations.AptAnnotation");
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.WARNING, "Test -> 1111");;
        return types;
    }

    /**
     * 文件生成器
     */
    private Filer mFiler;

    /**
     * 模块名称
     */
    private String mModuleName;

    Messager messager;


    /**
     * 初始化
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mModuleName = processingEnv.getOptions().get("MODULE_NAME");
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "打印信息");
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "AnnotationCompiler init");
        messager.printMessage(Diagnostic.Kind.NOTE, "MODULE_NAME is " + mModuleName);
    }

    /**
     * 编写生成 Java 类的相关逻辑
     *
     * @param annotations 支持处理的注解集合
     * @param roundEnv    通过该对象查找指定注解下的节点信息
     * @return true: 表示注解已处理，后续注解处理器无需再处理它们；false: 表示注解未处理，可能要求后续注解处理器处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "打印信息");

        // 获取当前注解下的节点信息
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(AptAnnotation.class);

        // 构建test()
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test")
                .addModifiers(Modifier.PUBLIC)// 修饰符
                .returns(void.class) // 返回类型
                .addParameter(String.class, "parm");// 参数
        builder.addStatement("$T.out.println($S)", System.class, "模块: " + mModuleName);

        if (elementsAnnotatedWith != null && !elementsAnnotatedWith.isEmpty()) {
            for (Element element : elementsAnnotatedWith) {
                // 当前节点名称
                String elementName = element.getSimpleName().toString();
                // 当前节点下注解的属性
                String desc = element.getAnnotation(AptAnnotation.class).desc();
                // 构建方法体
                builder.addStatement("$T.out.println($S)", System.class, "节点: " + elementName + "    " + "描述: " + desc);
            }
        }

        MethodSpec methodSpec = builder.build();
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        JavaFile javaFile = JavaFile.builder("com.dullfan.aptannotationdemo", typeSpec).build();
        messager.printMessage(Diagnostic.Kind.NOTE, "打印信息");
        System.out.println("文本");
        try {
            // 创建文件
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}