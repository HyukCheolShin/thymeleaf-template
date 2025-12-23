package com.example.thymeleaf_template.service.impl;

import com.example.thymeleaf_template.mapper.TestMapper;
import com.example.thymeleaf_template.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestMapper testMapper;

    @Override
    public String getDbTime() {
        return testMapper.selectNow();
    }
}
