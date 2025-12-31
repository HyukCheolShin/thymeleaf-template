package com.example.template.service;

import com.example.template.mapper.SampleMapper;
import com.example.template.common.dto.PageRequest;
import com.example.template.common.dto.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import com.example.template.common.exception.ResourceNotFoundException;

@Service
public class SampleService {

    private final SampleMapper sampleMapper;

    public SampleService(SampleMapper sampleMapper) {
        this.sampleMapper = sampleMapper;
    }

    public PageResponse<Map<String, Object>> getAllSamples(PageRequest pageRequest) {
        Map<String, Object> params = Map.of(
                "limit", pageRequest.getSize(),
                "offset", pageRequest.getOffset(),
                "keyword", pageRequest.getKeyword(),
                "searchType", pageRequest.getSearchType());

        int totalItems = sampleMapper.count(params);
        List<Map<String, Object>> list = sampleMapper.findAll(params);

        return new PageResponse<>(list, totalItems, pageRequest.getSize(), pageRequest.getPage());
    }

    public Map<String, Object> getSampleById(Long id) {
        return Optional.ofNullable(sampleMapper.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Sample not found with id: " + id));
    }

    public void saveSample(Map<String, Object> params) {
        validate(params);
        sampleMapper.insert(params);
    }

    public void updateSample(Map<String, Object> params) {
        validate(params);
        sampleMapper.update(params);
    }

    private void validate(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("Request body cannot be empty");
        }

        Stream.of("name", "email", "password", "role")
                .filter(field -> {
                    Object value = params.get(field);
                    return value == null || value.toString().trim().isEmpty();
                })
                .findFirst()
                .ifPresent(field -> {
                    throw new IllegalArgumentException("Field '" + field + "' is required");
                });
    }

    public void deleteSample(Long id) {
        sampleMapper.delete(id);
    }
}
