package blossom.autoconfigure.exclude;

import java.lang.annotation.Annotation;
import java.util.List;

public interface BlossomExcludeAnnotationTypeSupplier {

    List<Class<? extends Annotation>> get();
}
