package blossom.cglib;

import com.github.charlemaznable.httpclient.ohclient.enhancer.OhClientEnhancer;
import com.google.auto.service.AutoService;
import net.sf.cglib.proxy.Callback;

import static blossom.common.BlossomElf.isFastBlossomAnnotated;

@AutoService(OhClientEnhancer.class)
public class BlossomOhClientEnhancer implements OhClientEnhancer {

    @Override
    public boolean isEnabled(Class<?> clientClass) {
        return isFastBlossomAnnotated(clientClass);
    }

    @Override
    public Callback build(Class<?> clientClass, Object clientImpl) {
        return new BlossomCglibInterceptor(clientImpl);
    }
}
