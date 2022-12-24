package blossom.enhance;

import blossom.common.BlossomInterceptor;
import com.github.charlemaznable.core.lang.BuddyEnhancer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import javax.annotation.Nonnull;

import static com.github.charlemaznable.core.lang.Condition.checkNotNull;

@AllArgsConstructor
public final class BlossomBuddyInterceptor extends BlossomInterceptor<BuddyEnhancer.Invocation> implements BuddyEnhancer.Delegate {

    @Nonnull
    private Object target;

    @Override
    public Object invoke(BuddyEnhancer.Invocation invocation) {
        checkNotNull(target);
        val fqcn = invocation.getThis().getClass().getName();
        return super.intercept(fqcn, invocation.getThis(),
                invocation.getMethod(), invocation.getArguments(), invocation);
    }

    @SneakyThrows
    @Override
    protected Object invokeRaw(BuddyEnhancer.Invocation invocation) {
        return invocation.getMethod().invoke(target, invocation.getArguments());
    }
}
