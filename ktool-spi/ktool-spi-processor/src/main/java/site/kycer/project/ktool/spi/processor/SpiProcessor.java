package site.kycer.project.ktool.spi.processor;

import site.kycer.project.ktool.spi.annotations.SPI;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SpiProcessor
 *
 * @author Kycer
 * @version 1.0.0
 * @date 2020-03-24
 */
@SupportedAnnotationTypes(value = {"site.kycer.project.ktool.spi.annotations.SPI"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class SpiProcessor extends AbstractProcessor {

    private Map<String, String> providers = new ConcurrentHashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            if (roundEnv.processingOver()) {
                generateFiles();
            } else {
                processAnnotations(annotations, roundEnv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void generateFiles() {
        Filer filer = processingEnv.getFiler();
        providers.forEach((key, value) -> {
            try {
                String resourceFile = "META-INF/services/" + key;
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceFile);

                Writer writer = resource.openWriter();
                writer.write(value);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(SPI.class);

        for (Element element : elements) {
            TypeElement providerImplementer = (TypeElement) element;
            // 获取所有实现的接口
            List<? extends TypeMirror> interfaces = providerImplementer.getInterfaces();
            // 获取所有实现的注解
            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
            // 获取SPI注解元素
            Optional<? extends AnnotationMirror> optionalAnnotationMirror = annotationMirrors.stream()
                    .filter(annotation -> Objects.equals(annotation.getAnnotationType().asElement().getSimpleName().toString(), SPI.class.getSimpleName()))
                    .findAny();

            if (!optionalAnnotationMirror.isPresent()) {
                continue;
            }
            AnnotationMirror annotationMirror = optionalAnnotationMirror.get();
            // 获取当前节点类名
            String className = getBinaryName(providerImplementer);

            // 获取所有字段和值遍历
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
                ExecutableElement key = entry.getKey();
                AnnotationValue value = entry.getValue();
                String elementValue = value.getValue().toString();
                String interfaceName = elementValue.substring(0, elementValue.lastIndexOf('.'));
                // 只有实现了注解中包含接口的才会添加
                boolean match = interfaces.stream().map(Object::toString).anyMatch(i -> i.equals(interfaceName));
                if (!match) {
                    continue;
                }
                if (providers.containsKey(interfaceName)) {
                    className += "\n" + providers.get(interfaceName);
                }
                providers.put(interfaceName, className);
            }
        }
    }


    private String getBinaryName(TypeElement element) {
        return getBinaryNameImpl(element, element.getSimpleName().toString());
    }

    private String getBinaryNameImpl(TypeElement element, String className) {
        Element enclosingElement = element.getEnclosingElement();

        if (enclosingElement.getKind() == ElementKind.PACKAGE) {
            PackageElement pkg = (PackageElement) enclosingElement;
            if (pkg.isUnnamed()) {
                return className;
            }
            return pkg.getQualifiedName() + "." + className;
        }

        TypeElement typeElement = (TypeElement) enclosingElement;
        return getBinaryNameImpl(typeElement, typeElement.getSimpleName() + "$" + className);
    }

}
