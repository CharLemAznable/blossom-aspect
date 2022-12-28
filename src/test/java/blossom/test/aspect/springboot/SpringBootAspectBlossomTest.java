package blossom.test.aspect.springboot;

import blossom.test.aspect.TestAspectBlossom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest(classes = {SpringBootAspectBlossomApp.class})
@AutoConfigureMockMvc
public class SpringBootAspectBlossomTest extends TestAspectBlossom {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSpringBootAspectBlossom() {
        testWithMockMvc(mockMvc);
    }
}
