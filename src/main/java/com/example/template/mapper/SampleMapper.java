package com.example.template.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SampleMapper {
    List<Map<String, Object>> findAll(Map<String, Object> params);

    int count(Map<String, Object> params);

    Map<String, Object> findById(Long id);

    void insert(Map<String, Object> params);

    void update(Map<String, Object> params);

    void delete(Long id);
}
