package blossom.enhance;

import com.github.charlemaznable.core.lang.BuddyEnhancer;
import com.google.auto.service.AutoService;
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
        return BuddyEnhancer.create(Object.class,
                new Class[]{eqlerClass},
                new BlossomBuddyInterceptor(implObject));
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
