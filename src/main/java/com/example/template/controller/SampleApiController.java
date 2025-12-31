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
import com.example.template.common.dto.ApiResponse;
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
    public ApiResponse<PageResponse<Map<String, Object>>> getAllSamples(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String searchType) {
        return ApiResponse.success(sampleService.getAllSamples(new PageRequest(page, size, keyword, searchType)));
    }

    @GetMapping("/{id}")
    public ApiResponse<Map<String, Object>> getSampleById(@PathVariable Long id) {
        return ApiResponse.success(sampleService.getSampleById(id));
    }

    @PostMapping("/")
    public ApiResponse<Void> saveSample(@RequestBody Map<String, Object> params) {
        sampleService.saveSample(params);
        return ApiResponse.success();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateSample(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        params.put("id", id);
        sampleService.updateSample(params);
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSample(@PathVariable Long id) {
        sampleService.deleteSample(id);
        return ApiResponse.success();
    }
}
