package blossom.common;

import blossom.Blossom;
import com.google.common.cache.LoadingCache;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.event.Level;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Optional;

import static com.github.charlemaznable.core.lang.LoadingCachee.get;
import static com.github.charlemaznable.core.lang.LoadingCachee.simpleCache;
import static com.google.common.cache.CacheLoader.from;
import static org.springframework.core.annotation.AnnotatedElementUtils.getMergedAnnotation;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlossomCaches {

    private static final LoadingCache<Class, Optional<Blossom>> classAnnotationCache
            = simpleCache(from(BlossomCaches::lookupClassAnnotationCache));
    private static final LoadingCache<Method, Optional<Blossom>> methodAnnotationCache
            = simpleCache(from(BlossomCaches::lookupMethodAnnotationCache));

    private static final LoadingCache<Class, Optional<Level>> classLevelCache
            = simpleCache(from(BlossomCaches::lookupClassLevelCache));
    private static final LoadingCache<Method, Optional<Level>> methodLevelCache
            = simpleCache(from(BlossomCaches::lookupMethodLevelCache));

    public static Optional<Blossom> getBlossomAnnotation(Class clazz) {
        return get(classAnnotationCache, clazz);
    }

    public static Optional<Blossom> getBlossomAnnotation(Method method) {
        return get(methodAnnotationCache, method);
    }

    public static boolean isBlossomAnnotated(Class clazz) {
        return getBlossomAnnotation(clazz).isPresent();
    }

    public static boolean isBlossomAnnotated(Method method) {
        return getBlossomAnnotation(method).isPresent();
    }

    public static Optional<Level> getBlossomLevel(Class clazz) {
        return get(classLevelCache, clazz);
    }

    public static Optional<Level> getBlossomLevel(Method method) {
        return get(methodLevelCache, method);
    }

    @Nonnull
    private static Optional<Blossom> lookupClassAnnotationCache(@Nonnull Class clazz) {
        return Optional.ofNullable(getMergedAnnotation(clazz, Blossom.class));
    }

    @Nonnull
    private static Optional<Blossom> lookupMethodAnnotationCache(@Nonnull Method method) {
        return Optional.ofNullable(getMergedAnnotation(method, Blossom.class));
    }

    @Nonnull
    private static Optional<Level> lookupClassLevelCache(@Nonnull Class clazz) {
        return getBlossomAnnotation(clazz).map(blossom -> Level.valueOf(blossom.value().name()));
    }

    @Nonnull
    private static Optional<Level> lookupMethodLevelCache(@Nonnull Method method) {
        return getBlossomAnnotation(method).map(blossom -> Level.valueOf(blossom.value().name()));
    }
}
