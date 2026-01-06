package com.example.template.controller;

import com.example.template.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.template.common.dto.ApiResponse;
import com.example.template.common.dto.PageRequest;
import com.example.template.common.dto.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/")
    public ApiResponse<PageResponse<Map<String, Object>>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String searchType) {
        return ApiResponse.success(userService.getAllUsers(new PageRequest(page, size, keyword, searchType)));
    }

    @GetMapping("/{id}")
    public ApiResponse<Map<String, Object>> getUserById(@PathVariable Long id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ApiResponse<Long> saveUser(
            @RequestParam Map<String, Object> params,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        Long id = userService.saveUser(params, file);
        return ApiResponse.success(id);
    }

    @PostMapping(value = "/{id}", consumes = "multipart/form-data")
    public ApiResponse<Void> updateUser(
            @PathVariable Long id,
            @RequestParam Map<String, Object> params,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        params.put("id", id);
        userService.updateUser(params, file);
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.success();
    }
}
