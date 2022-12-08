package blossom.autoconfigure.exclude;

import com.github.charlemaznable.core.lang.ClzPath;
import com.github.charlemaznable.httpclient.ohclient.OhClient;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

@AutoService(BlossomExcludeAnnotationTypeSupplier.class)
public class ExcludeOhClientSupplier implements BlossomExcludeAnnotationTypeSupplier {

    private static final boolean HAS_OH_CLIENT;

    static {
        HAS_OH_CLIENT = ClzPath.classExists("com.github.charlemaznable.httpclient.ohclient.OhClient");
    }

    @Override
    public List<Class<? extends Annotation>> get() {
        if (HAS_OH_CLIENT) return newArrayList(OhClient.class);
        return emptyList();
    }
}
