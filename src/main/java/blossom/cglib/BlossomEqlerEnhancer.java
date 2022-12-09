package blossom.cglib;

import com.google.auto.service.AutoService;
import net.sf.cglib.proxy.Enhancer;
import org.n3r.eql.eqler.enhancer.EqlerEnhancer;

import static blossom.common.BlossomElf.isFastBlossomAnnotated;

@AutoService(EqlerEnhancer.class)
public class BlossomEqlerEnhancer implements EqlerEnhancer {

    @Override
    public boolean isEnabled(Class eqlerClass) {
        return isFastBlossomAnnotated(eqlerClass);
    }

    @Override
    public Object build(Class eqlerClass, Object implObject) {
        return Enhancer.create(Object.class, new Class[]{eqlerClass},
                new BlossomCglibInterceptor(implObject));
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
