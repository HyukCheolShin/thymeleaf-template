package com.example.thymeleaf_template.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    String selectNow();

    Map<String, Object> selectParam(Map<String, Object> param);

    List<Map<String, Object>> selectParamList(Map<String, Object> param);
}
