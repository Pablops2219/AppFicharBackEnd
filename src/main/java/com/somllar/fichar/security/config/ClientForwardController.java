package com.somllar.fichar.security.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientForwardController implements ErrorController {
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String forward() {
        return "forward:/";
    }

    @RequestMapping(value = "${server.error.path:${error.path:/error}}")
    public String error() {
        return "forward:/index.html";
    }
}
