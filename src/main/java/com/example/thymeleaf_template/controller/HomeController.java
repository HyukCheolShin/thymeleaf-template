package com.example.thymeleaf_template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/","/home.do"})
    public String home() {
        // templates/home.html 로 이동
        return "home";
    }
}
