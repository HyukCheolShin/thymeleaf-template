package com.example.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/samples")
public class SampleController {

    @GetMapping
    public String list() {
        return "sample/list";
    }
}
