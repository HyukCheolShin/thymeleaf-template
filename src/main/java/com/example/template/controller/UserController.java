package com.example.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 화면 이동을 담당하는 컨트롤러
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("")
    public String list() {
        return "user/list";
    }

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/{id}")
    public String form(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "user/form";
    }
}
