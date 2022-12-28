package blossom.spring.exclude;

import com.github.charlemaznable.core.lang.ClzPath;
import com.google.auto.service.AutoService;
import org.n3r.eql.eqler.annotations.Eqler;
import org.n3r.eql.eqler.annotations.EqlerConfig;

import java.lang.annotation.Annotation;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

@AutoService(BlossomExcludeAnnotationTypeSupplier.class)
public class ExcludeEqlerSupplier implements BlossomExcludeAnnotationTypeSupplier {

    private static final boolean HAS_EQLER;

    static {
        HAS_EQLER = ClzPath.classExists("org.n3r.eql.eqler.annotations.Eqler");
    }

    @Override
    public List<Class<? extends Annotation>> get() {
        if (HAS_EQLER) return newArrayList(Eqler.class, EqlerConfig.class);
        return emptyList();
    }
}
