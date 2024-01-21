package tobyspring.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class MyConfigImportSelector implements DeferredImportSelector{
    private final ClassLoader classLoader;

    public MyConfigImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
//        return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);
        List<String> autoConfigs = new ArrayList<>();
//        ImportCandidates.load(MyConfigImportSelector.class, classLoader).forEach(autoConfigs::add);
        for (String candidate : candidates) {
            autoConfigs.add(candidate);
        }

        // return autoConfigs.toArray(new String[0]);
        return autoConfigs.stream().toArray(String[]::new);

//        return new String[] {
//                "tobyspring.config.autoconfig.TomcatWebServerConfig",
//                "tobyspring.config.autoconfig.DispatcherServletConfig"
//        };
    }
}
