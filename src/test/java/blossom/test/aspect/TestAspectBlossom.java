package blossom.test.aspect;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class TestAspectBlossom {

    @SneakyThrows
    public void testWithMockMvc(MockMvc mockMvc) {
        val responseNone = mockMvc.perform(get("/testBlossomAspectNone")
                        .param("param", "none").content(""))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertEquals("[none]", responseNone.getContentAsString());

        val responseAOP = mockMvc.perform(get("/testBlossomAspect")
                        .param("param", "aop").content(""))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertEquals("{aop}", responseAOP.getContentAsString());

        val responseAOPException = mockMvc.perform(get("/testBlossomAspectException")
                        .param("param", "exception").content(""))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertEquals("{exception}", responseAOPException.getContentAsString());
    }
}
