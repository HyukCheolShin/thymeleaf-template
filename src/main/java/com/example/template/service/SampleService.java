package com.example.template.service;

import com.example.template.mapper.SampleMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class SampleService {

    private final SampleMapper sampleMapper;

    public SampleService(SampleMapper sampleMapper) {
        this.sampleMapper = sampleMapper;
    }

    public List<Map<String, Object>> getAllSamples() {
        return sampleMapper.findAll();
    }

    public Map<String, Object> getSampleById(Long id) {
        return sampleMapper.findById(id);
    }
}
