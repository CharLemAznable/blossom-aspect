package blossom.autoconfigure;

import blossom.Blossom;
import com.github.charlemaznable.core.net.Http;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAOPController {

    @Blossom
    @RequestMapping("/testBlossomAOP")
    public String testBlossomAOP(@RequestParam String param) {
        return "{" + param + "}";
    }

    @Blossom
    @RequestMapping("/testBlossomAOPException")
    public String testBlossomAOPException(@RequestParam String param) {
        throw new RuntimeException(param);
    }

    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException exception,
                                       HttpServletResponse response) {
        Http.responseText(response, "{" + exception.getMessage() + "}");
    }
}
