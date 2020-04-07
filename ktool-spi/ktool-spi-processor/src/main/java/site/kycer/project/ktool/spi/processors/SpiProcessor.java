package site.kycer.project.ktool.spi.processors;

import site.kycer.project.ktool.spi.annotations.SPI;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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

    public static final String META_INF_SERVICES = "META-INF/services/";
    private Map<String, Set<String>> providers = new ConcurrentHashMap<>();

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
        return false;
    }

    private void generateFiles() {
        Filer filer = processingEnv.getFiler();
        for (Map.Entry<String, Set<String>> entry : providers.entrySet()) {
            try {
                String contract = entry.getKey();
                FileObject fileObject = filer.getResource(StandardLocation.CLASS_OUTPUT, "", META_INF_SERVICES + contract);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileObject.openInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    entry.getValue().add(line);
                }
                reader.close();
            } catch (FileNotFoundException | NoSuchFileException x) {
                // doesn't exist
            } catch (IOException x) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to load existing service definition files: " + x);
            }
        }

        for (Map.Entry<String, Set<String>> entry : providers.entrySet()) {
            try {
                String contract = entry.getKey();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Writing META-INF/services/" + contract);
                FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", META_INF_SERVICES + contract);
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(fileObject.openOutputStream(), StandardCharsets.UTF_8));
                for (String value : entry.getValue()) {
                    writer.println(value);
                }
                writer.close();
            } catch (IOException x) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to write service definition files: " + x);
            }
        }


    }

    private void processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(SPI.class);

        Elements elementUtils = processingEnv.getElementUtils();

        for (Element element : elements) {
            // 获取SPI注解
            final SPI spi = element.getAnnotation(SPI.class);

            if (spi == null) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "spi is null");
                continue;
            }

            if (!element.getKind().isClass() && !element.getKind().isInterface()) {
                continue;
            }

            TypeElement typeElement = (TypeElement) element;

            Collection<TypeElement> contracts = this.getTypeElements(typeElement, spi);
            if (contracts.isEmpty()) {
                continue;
            }

            for (TypeElement contract : contracts) {
                String interfaceName = elementUtils.getBinaryName(contract).toString();
                Set<String> values = providers.computeIfAbsent(interfaceName, k -> new TreeSet<>());
                values.add(elementUtils.getBinaryName(typeElement).toString());
            }
        }
    }


    private Collection<TypeElement> getTypeElements(TypeElement typeElement, SPI spi) {
        List<TypeElement> typeElementList = new ArrayList<>();

        try {
            spi.value();
            throw new AssertionError();
        } catch (MirroredTypesException e) {

            for (TypeMirror mirror : e.getTypeMirrors()) {
                if (mirror.getKind() == TypeKind.VOID) {
                    // contract inferred from the signature
                    boolean hasBaseClass = typeElement.getSuperclass().getKind() != TypeKind.NONE && !isObject(typeElement.getSuperclass());
                    boolean hasInterfaces = !typeElement.getInterfaces().isEmpty();
                    if (hasBaseClass ^ hasInterfaces) {
                        if (hasBaseClass) {
                            typeElementList.add((TypeElement) ((DeclaredType) typeElement.getSuperclass()).asElement());
                        } else {
                            typeElementList.add((TypeElement) ((DeclaredType) typeElement.getInterfaces().get(0)).asElement());
                        }
                        continue;
                    }
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Contract type was not specified, but it couldn't be inferred.", typeElement);
                    continue;
                }

                if (mirror instanceof DeclaredType) {
                    DeclaredType dt = (DeclaredType) mirror;
                    typeElementList.add((TypeElement) dt.asElement());
                } else {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Invalid type specified as the contract", typeElement);
                }
            }
        }
        return typeElementList;
    }

    private boolean isObject(TypeMirror typeMirror) {
        if (typeMirror instanceof DeclaredType) {
            DeclaredType dt = (DeclaredType) typeMirror;
            return "java.lang.Object".equals(((TypeElement) dt.asElement()).getQualifiedName().toString());
        }
        return false;
    }

}
