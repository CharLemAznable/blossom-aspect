package blossom.autoconfigure;

import lombok.SneakyThrows;
import lombok.val;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAOPConfig.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BlossomAOPTest {

    private static MockMvc mockMvc;
    @Autowired
    private TestAOPNoneController testNoneController;
    @Autowired
    private TestAOPController testController;

    @BeforeAll
    public void beforeAll() {
        mockMvc = MockMvcBuilders.standaloneSetup(testNoneController, testController).build();
    }

    @SneakyThrows
    @Test
    public void testBlossomAOP() {
        val responseNone = mockMvc.perform(get("/testBlossomNone").param("param", "none"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertEquals("[none]", responseNone.getContentAsString());

        val responseAOP = mockMvc.perform(get("/testBlossomAOP").param("param", "aop"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertEquals("{aop}", responseAOP.getContentAsString());

        val responseAOPException = mockMvc.perform(get("/testBlossomAOPException").param("param", "exception"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertEquals("{exception}", responseAOPException.getContentAsString());
    }
}
