package kr.co.aia.ipro.secondary.controller;

import kr.co.aia.ipro.secondary.service.SecondaryTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secondary")
@RequiredArgsConstructor
public class SecondaryTestController {

    private final SecondaryTestService secondaryTestService;

    @GetMapping("/test")
    public String testSecondaryConnection() {
        return "Secondary DB Time: " + secondaryTestService.getSecondaryDbTime();
    }
}
