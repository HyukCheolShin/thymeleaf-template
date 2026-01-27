package kr.co.aia.dmd.ipro.secondary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.aia.dmd.ipro.secondary.service.SecondaryTestService;

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
