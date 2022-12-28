package blossom.test.aspect.spring;

import blossom.test.aspect.TestAspectBlossom;
import blossom.test.aspect.TestAspectController;
import blossom.test.aspect.TestAspectNoneController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringAspectBlossomConfig.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpringAspectBlossomTest extends TestAspectBlossom {

    private static MockMvc mockMvc;
    @Autowired
    private TestAspectNoneController testNoneController;
    @Autowired
    private TestAspectController testController;

    @BeforeAll
    public void beforeAll() {
        mockMvc = MockMvcBuilders.standaloneSetup(testNoneController, testController).build();
    }

    @Test
    public void testSpringAspectBlossom() {
        testWithMockMvc(mockMvc);
    }
}
