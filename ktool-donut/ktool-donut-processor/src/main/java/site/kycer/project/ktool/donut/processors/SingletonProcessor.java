package site.kycer.project.ktool.donut.processoies;

import site.kycer.project.ktool.donut.annotations.Singleton;
import site.kycer.project.ktool.spi.annotations.SPI;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * SingletonProcessor
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-04-02
 */
@SupportedAnnotationTypes(value = {"site.kycer.project.ktool.donut.annotations.Singleton"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@SPI(Processor.class)
public class SingletonProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        StringBuilder builder = new StringBuilder()
                .append("package com.yuntao.annotationprocessor.generated;\n\n")
                .append("public class GeneratedClass {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");


        for (Element element : roundEnv.getElementsAnnotatedWith(Singleton.class)) {
            String objectType = element.getSimpleName().toString();


            // this is appending to the return statement
            builder.append(objectType).append(" says hello!\\n");

            builder.append("\";\n") // end return
                    .append("\t}\n") // close method
                    .append("}\n"); // close class


            try { // write the file
                JavaFileObject source = processingEnv.getFiler().createSourceFile("com.yuntao.annotationprocessor.generated.GeneratedClass");


                Writer writer = source.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // Note: calling e.printStackTrace() will print IO errors
                // that occur from the file already existing after its first run, this is normal
            }
        }



        return true;
    }
}
