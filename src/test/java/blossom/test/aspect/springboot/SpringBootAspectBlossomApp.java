package blossom.test.aspect.springboot;

import blossom.test.aspect.TestAspectConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TestAspectConfig.class)
public class SpringBootAspectBlossomApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAspectBlossomApp.class, args);
    }
}
