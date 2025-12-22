package com.example.thymeleaf_template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login.do")
    public String login() {
        // templates/login.html 로 이동
        return "login";
    }
}
