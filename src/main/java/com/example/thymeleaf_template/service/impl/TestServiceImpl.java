package com.example.thymeleaf_template.service.impl;

import com.example.thymeleaf_template.mapper.TestMapper;
import com.example.thymeleaf_template.service.TestService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;

    @Override
    public String getDbTime() {
        return testMapper.selectNow();
    }

    @Override
    public Map<String, Object> getParam(Map<String, Object> param) {
        return testMapper.selectParam(param);
    }

    @Override
    public List<Map<String, Object>> getParamList(Map<String, Object> param) {
        return testMapper.selectParamList(param);
    }
}
