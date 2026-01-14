package com.example.thymeleaf_template.service;

import java.util.List;
import java.util.Map;

public interface TestService {
    String getDbTime();

    Map<String, Object> getParam(Map<String, Object> param);

    List<Map<String, Object>> getParamList(Map<String, Object> param);
}
