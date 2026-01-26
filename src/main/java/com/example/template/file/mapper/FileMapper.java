package com.example.template.file.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {
    void insert(Map<String, Object> map);

    List<Map<String, Object>> findByRef(Map<String, Object> map);

    Map<String, Object> findById(Long id);

    void delete(Long id);

    void deleteByRef(Map<String, Object> map);
}
