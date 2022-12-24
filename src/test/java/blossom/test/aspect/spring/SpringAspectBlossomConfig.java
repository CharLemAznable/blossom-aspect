package blossom.test.aspect.spring;

import blossom.spring.BlossomImport;
import blossom.test.aspect.TestAspectConfig;
import org.springframework.context.annotation.Import;

@BlossomImport
@Import(TestAspectConfig.class)
public class SpringAspectBlossomConfig {
}
