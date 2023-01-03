package blossom.test.aspect.spring;

import blossom.test.aspect.TestAspectBlossom;
import blossom.test.aspect.TestAspectController;
import blossom.test.aspect.TestAspectNoneController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringJUnitWebConfig(SpringAspectBlossomConfig.class)
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
