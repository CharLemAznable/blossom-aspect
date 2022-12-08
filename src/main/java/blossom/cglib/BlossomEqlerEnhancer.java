package blossom.cglib;

import blossom.Blossom;
import com.github.charlemaznable.core.lang.ClzPath;
import com.google.auto.service.AutoService;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.val;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.SuperMethodCall;
import net.sf.cglib.proxy.Enhancer;
import org.apache.commons.lang3.tuple.Pair;
import org.n3r.eql.eqler.enhancer.EqlerEnhancer;

import javax.annotation.Nonnull;
import java.util.function.Function;

import static blossom.common.BlossomCaches.getBlossomAnnotation;
import static blossom.common.BlossomElf.isFastBlossomAnnotated;
import static com.github.charlemaznable.core.lang.LoadingCachee.get;
import static com.github.charlemaznable.core.lang.LoadingCachee.simpleCache;
import static com.google.common.cache.CacheLoader.from;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

@AutoService(EqlerEnhancer.class)
public class BlossomEqlerEnhancer implements EqlerEnhancer {

    private static final boolean HAS_BYTE_BUDDY;
    private static final LoadingCache<Pair<Class, Class<?>>, Class<?>> annotatedClasses;

    static {
        HAS_BYTE_BUDDY = ClzPath.classExists("net.bytebuddy.ByteBuddy");
        annotatedClasses = HAS_BYTE_BUDDY ? simpleCache(from(
                BlossomEqlerEnhancer::annotatedEqlerImplClass)) : null;
    }

    @Override
    public boolean isEnabled(Class eqlerClass) {
        return isFastBlossomAnnotated(eqlerClass);
    }

    @SneakyThrows
    @Override
    public Object build(Class eqlerClass, Object implObject) {
        if (HAS_BYTE_BUDDY) {
            val annotatedClass = get(annotatedClasses,
                    Pair.of(eqlerClass, implObject.getClass()));
            return Enhancer.create(annotatedClass,
                    new BlossomCglibInterceptor(annotatedClass.newInstance()));
        }
        return Enhancer.create(Object.class, new Class[]{eqlerClass},
                new BlossomCglibInterceptor(implObject));
    }

    @Nonnull
    private static Class<?> annotatedEqlerImplClass(@Nonnull Pair<Class, Class<?>> classPair) {
        val eqlerClass = classPair.getLeft();
        val implClass = classPair.getRight();
        Builder builder = new ByteBuddy().subclass(implClass);
        builder = getBlossomAnnotation(eqlerClass)
                .map((Function<Blossom, Builder>)
                        builder::annotateType).orElse(builder);
        for (val method : eqlerClass.getMethods()) {
            val finalBuilder = builder;
            builder = getBlossomAnnotation(method)
                    .map((Function<Blossom, Builder>) blossom ->
                            finalBuilder.method(named(method.getName())
                                    .and(takesArguments(method.getParameterTypes())))
                                    .intercept(SuperMethodCall.INSTANCE)
                                    .annotateMethod(blossom)).orElse(builder);
        }
        return builder.make().load(implClass.getClassLoader()).getLoaded();
    }
}
