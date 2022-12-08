package blossom.autoconfigure;

import blossom.autoconfigure.exclude.BlossomExcludes;
import lombok.val;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

import static blossom.common.BlossomCaches.isBlossomAnnotated;
import static blossom.common.BlossomElf.hasAnnotationInHierarchy;

final class BlossomMethodPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, @Nonnull Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) return false;
        for (val anno : BlossomExcludes.excludeAnnoTypes()) {
            if (hasAnnotationInHierarchy(anno, targetClass)) return false;
        }
        return isBlossomAnnotated(method) || isBlossomAnnotated(targetClass);
    }
}
