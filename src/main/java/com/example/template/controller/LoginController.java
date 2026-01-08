package com.example.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

@Controller
@Slf4j
public class LoginController {

    @GetMapping({ "/", "/login" })
    public String index(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            logger.info("User is authenticated");
            return "redirect:/users";
        }
        return "login";
    }
}
