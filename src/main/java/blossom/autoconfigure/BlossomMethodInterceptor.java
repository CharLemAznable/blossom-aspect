package blossom.autoconfigure;

import blossom.Blossoms;
import blossom.common.BlossomInterceptor;
import lombok.SneakyThrows;
import lombok.val;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;

final class BlossomMethodInterceptor extends BlossomInterceptor<MethodInvocation> implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) {
        val fqcn = (invocation instanceof ProxyMethodInvocation)
                ? ((ProxyMethodInvocation) invocation).getProxy().getClass().getName()
                : Blossoms.class.getName();
        return super.intercept(fqcn, invocation.getThis(),
                invocation.getMethod(), invocation.getArguments(), invocation);
    }

    @SneakyThrows
    @Override
    protected Object invokeRaw(MethodInvocation methodProxy) {
        return methodProxy.proceed();
    }
}
