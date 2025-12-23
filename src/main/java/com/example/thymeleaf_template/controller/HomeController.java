package com.example.thymeleaf_template.controller;

import com.example.thymeleaf_template.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TestService testService;

    @GetMapping({ "/", "/home.do" })
    public String home(Model model) {
        String dbTime = testService.getDbTime();
        log.info("Database Current Time via Service: {}", dbTime);
        model.addAttribute("dbTime", dbTime);

        // templates/home.html 로 이동
        return "home";
    }
}
