package blossom.autoconfigure;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAOPNoneController {

    @RequestMapping("/testBlossomNone")
    public String testBlossomNone(@RequestParam String param) {
        return "[" + param + "]";
    }
}
