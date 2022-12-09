package blossom.autoconfigure.exclude;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import static com.google.common.collect.Lists.newArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlossomExcludes {

    private static List<Class<? extends Annotation>> excludeAnnoTypes = newArrayList();

    static {
        val supplierLoaders = ServiceLoader.load(BlossomExcludeAnnotationTypeSupplier.class);
        for (val supplierLoader : supplierLoaders) {
            Optional.ofNullable(supplierLoader.get()).ifPresent(
                    annotations -> excludeAnnoTypes.addAll(annotations));
        }
    }

    public static List<Class<? extends Annotation>> excludeAnnoTypes() {
        return excludeAnnoTypes;
    }
}