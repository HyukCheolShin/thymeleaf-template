package com.example.template.service;

import com.example.template.mapper.UserMapper;
import com.example.template.common.dto.PageRequest;
import com.example.template.common.dto.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import com.example.template.common.exception.ResourceNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashMap;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public PageResponse<Map<String, Object>> getAllUsers(PageRequest pageRequest) {
        Map<String, Object> params = Map.of(
                "limit", pageRequest.getSize(),
                "offset", pageRequest.getOffset(),
                "keyword", pageRequest.getKeyword(),
                "searchType", pageRequest.getSearchType());

        int totalItems = userMapper.count(params);
        List<Map<String, Object>> list = userMapper.findAll(params);

        return new PageResponse<>(list, totalItems, pageRequest.getSize(), pageRequest.getPage());
    }

    public Map<String, Object> getUserById(Long id) {
        return Optional.ofNullable(userMapper.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public void saveUser(Map<String, Object> params) {
        validate(params);

        Map<String, Object> mutableParams = new HashMap<>(params);
        String rawPassword = (String) mutableParams.get("password");
        mutableParams.put("password", passwordEncoder.encode(rawPassword));

        userMapper.insert(mutableParams);
    }

    public void updateUser(Map<String, Object> params) {
        validate(params);

        Map<String, Object> mutableParams = new HashMap<>(params);
        String rawPassword = (String) mutableParams.get("password");
        mutableParams.put("password", passwordEncoder.encode(rawPassword));

        userMapper.update(mutableParams);
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

    public void deleteUser(Long id) {
        userMapper.delete(id);
    }
}
