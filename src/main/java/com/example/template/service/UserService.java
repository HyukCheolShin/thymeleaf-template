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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Collection;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder, FileService fileService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    public PageResponse<Map<String, Object>> getAllUsers(PageRequest pageRequest) {
        Map<String, Object> params = Map.of(
                "limit", pageRequest.getSize(),
                "offset", pageRequest.getOffset(),
                "keyword", pageRequest.getKeyword(),
                "searchType", pageRequest.getSearchType());

        int totalItems = userMapper.count(params);
        List<Map<String, Object>> list = userMapper.findAll(params);

        if (!isAdmin()) {
            list.forEach(user -> user.remove("password"));
        }

        return new PageResponse<>(list, totalItems, pageRequest.getSize(), pageRequest.getPage());
    }

    public Map<String, Object> getUserById(Long id) {
        Map<String, Object> user = Optional.ofNullable(userMapper.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!isAdmin()) {
            user.remove("password");
        }

        return user;
    }

    private boolean isAdmin() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @Transactional
    public Long saveUser(Map<String, Object> params, MultipartFile file) {
        validate(params);

        Map<String, Object> mutableParams = new HashMap<>(params);
        String rawPassword = (String) mutableParams.get("password");
        mutableParams.put("password", passwordEncoder.encode(rawPassword));

        userMapper.insert(mutableParams);
        Long userId = (Long) mutableParams.get("id");

        if (file != null && !file.isEmpty()) {
            fileService.uploadFile(file, "users", userId);
        }

        return userId;
    }

    @Transactional
    public void updateUser(Map<String, Object> params, MultipartFile file) {
        validate(params);

        Map<String, Object> mutableParams = new HashMap<>(params);
        String rawPassword = (String) mutableParams.get("password");
        if (rawPassword != null && !rawPassword.trim().isEmpty()) {
            mutableParams.put("password", passwordEncoder.encode(rawPassword));
        } else {
            // If password is blank in update, maybe we shouldn't encode empty string or
            // should skip?
            // Existing logic forced encoding. But validate checks for password presence.
            // If update allows partial, validate might need adjustment, but current
            // validate enforces required fields.
            // I will stick to current logic: encode it.
            mutableParams.put("password", passwordEncoder.encode(rawPassword));
        }

        userMapper.update(mutableParams);

        if (file != null && !file.isEmpty()) {
            Long userId = (Long) mutableParams.get("id");
            fileService.uploadFile(file, "users", userId);
        }
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

    @Transactional
    public void deleteUser(Long id) {
        fileService.deleteFilesByRef("users", id);
        userMapper.delete(id);
    }
}
