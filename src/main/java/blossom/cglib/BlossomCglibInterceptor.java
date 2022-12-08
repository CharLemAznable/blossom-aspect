package blossom.cglib;

import blossom.common.BlossomInterceptor;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

import static com.github.charlemaznable.core.lang.Condition.checkNotNull;

@AllArgsConstructor
public final class BlossomCglibInterceptor extends BlossomInterceptor<MethodProxy> implements MethodInterceptor {

    @Nonnull
    private Object target;

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) {
        checkNotNull(target);
        return super.intercept(object.getClass().getName(), object, method, args, methodProxy);
    }

    @SneakyThrows
    @Override
    protected Object invokeRaw(Object object, Object[] args, MethodProxy methodProxy) {
        return methodProxy.invoke(target, args);
    }
}
