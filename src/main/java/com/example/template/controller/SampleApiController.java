package com.example.template.controller;

import com.example.template.service.SampleService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.template.common.dto.PageRequest;
import com.example.template.common.dto.PageResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/samples")
public class SampleApiController {

    private final SampleService sampleService;

    public SampleApiController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/")
    public PageResponse<Map<String, Object>> getAllSamples(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String searchType) {
        return sampleService.getAllSamples(new PageRequest(page, size, keyword, searchType));
    }

    @GetMapping("/{id}")
    public Map<String, Object> getSampleById(@PathVariable Long id) {
        return sampleService.getSampleById(id);
    }

    @PostMapping("/")
    public void saveSample(@RequestBody Map<String, Object> params) {
        sampleService.saveSample(params);
    }

    @PutMapping("/{id}")
    public void updateSample(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        params.put("id", id);
        sampleService.updateSample(params);
    }

    @DeleteMapping("/{id}")
    public void deleteSample(@PathVariable Long id) {
        sampleService.deleteSample(id);
    }
}
