package blossom.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import java.lang.annotation.Annotation;

import static blossom.common.BlossomCaches.isBlossomAnnotated;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BlossomElf {

    public static boolean isFastBlossomAnnotated(Class<?> clazz) {
        if (isBlossomAnnotated(clazz)) return true;
        for (val method : clazz.getMethods()) {
            if (isBlossomAnnotated(method)) return true;
        }
        return false;
    }

    public static boolean hasAnnotationInHierarchy(Class<? extends Annotation> annoClass, Class<?> clazz) {
        if (clazz.isAnnotationPresent(annoClass)) return true;
        for (val interfaceClass : clazz.getInterfaces()) {
            if (interfaceClass.isAnnotationPresent(annoClass)) return true;
        }
        return clazz.getSuperclass() != null && hasAnnotationInHierarchy(annoClass, clazz.getSuperclass());
    }
}
