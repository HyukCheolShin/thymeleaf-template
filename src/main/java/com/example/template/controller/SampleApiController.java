package com.example.template.controller;

import com.example.template.service.SampleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/samples")
public class SampleApiController {

    private final SampleService sampleService;

    public SampleApiController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/")
    public List<Map<String, Object>> getAllSamples() {
        return sampleService.getAllSamples();
    }

    @GetMapping("/{id}")
    public Map<String, Object> getSampleById(@PathVariable Long id) {
        return sampleService.getSampleById(id);
    }
}
