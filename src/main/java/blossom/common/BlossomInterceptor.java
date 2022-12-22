package blossom.common;

import blossom.Blossoms;
import com.google.common.cache.LoadingCache;
import lombok.Lombok;
import lombok.val;
import org.slf4j.event.Level;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

import static blossom.common.BlossomCaches.getBlossomLevel;
import static com.github.charlemaznable.core.lang.Listt.newArrayList;
import static com.github.charlemaznable.core.lang.LoadingCachee.get;
import static com.github.charlemaznable.core.lang.LoadingCachee.simpleCache;
import static com.google.common.cache.CacheLoader.from;
import static java.util.Objects.isNull;

public abstract class BlossomInterceptor<T> {

    private static final LoadingCache<Method, Boolean> voidMethodCache
            = simpleCache(from(BlossomInterceptor::lookupVoidMethod));

    protected abstract Object invokeRaw(T methodProxy);

    protected Object intercept(String fqcn, Object object, Method method, Object[] args, T methodProxy) {
        val level = parseLevel(object, method);
        if (isNull(level)) return invokeRaw(methodProxy);
        try {
            Blossoms.log(fqcn, level, "START: {}", newArrayList(args));
            val ret = invokeRaw(methodProxy);
            if (isVoidMethod(method)) Blossoms.log(fqcn, level, "FINISH: void");
            else Blossoms.log(fqcn, level, "FINISH: {}", newArrayList(ret));
            return ret;
        } catch (Exception exception) {
            Blossoms.log(fqcn, level, "EXCEPTION:", exception);
            throw Lombok.sneakyThrow(exception);
        }
    }

    private static Level parseLevel(Object object, Method method) {
        return getBlossomLevel(method).orElseGet(() ->
                getBlossomLevel(object.getClass()).orElse(null));
    }

    private static boolean isVoidMethod(Method method) {
        return get(voidMethodCache, method);
    }

    @Nonnull
    private static Boolean lookupVoidMethod(@Nonnull Method method) {
        val returnType = method.getReturnType();
        return void.class == returnType || Void.class == returnType;
    }
}
