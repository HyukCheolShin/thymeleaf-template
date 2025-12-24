package com.example.thymeleaf_template.controller;

import com.example.thymeleaf_template.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("param1", "value1");
        paramMap.put("param2", "value2");

        String dbTime = testService.getDbTime();
        log.info("testService.getDbTime() result : {}", dbTime);

        Map<String, Object> rtnMap = testService.getParam(paramMap);
        log.info("testService.getParam() result : {}", rtnMap);

        List<Map<String, Object>> rtnList = testService.getParamList(paramMap);
        log.info("testService.getParam() result : {}", rtnList);

        model.addAttribute("dbTime", dbTime);

        // templates/home.html 로 이동
        return "home";
    }
}
