package blossom.enhance;

import com.github.charlemaznable.core.lang.BuddyEnhancer;
import com.github.charlemaznable.core.lang.Reloadable;
import com.github.charlemaznable.httpclient.ohclient.enhancer.OhClientEnhancer;
import com.github.charlemaznable.httpclient.ohclient.internal.OhDummy;
import com.google.auto.service.AutoService;

import static blossom.common.BlossomElf.isFastBlossomAnnotated;

@AutoService(OhClientEnhancer.class)
public class BlossomOhClientEnhancer implements OhClientEnhancer {

    @Override
    public boolean isEnabled(Class<?> clientClass) {
        return isFastBlossomAnnotated(clientClass);
    }

    @Override
    public Object build(Class<?> clientClass, Object clientImpl) {
        return BuddyEnhancer.create(OhDummy.class,
                new Object[]{clientClass},
                new Class[]{clientClass, Reloadable.class},
                new BlossomBuddyInterceptor(clientImpl));
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
