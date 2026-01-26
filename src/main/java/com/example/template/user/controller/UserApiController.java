package com.example.template.user.controller;

import com.example.template.user.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.template.common.dto.ApiResponseDto;
import com.example.template.common.dto.PageRequestDto;
import com.example.template.common.dto.PageResponseDto;
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
    public ApiResponseDto<PageResponseDto<Map<String, Object>>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String searchType) {
        return ApiResponseDto.success(userService.getAllUsers(new PageRequestDto(page, size, keyword, searchType)));
    }

    @GetMapping("/{id}")
    public ApiResponseDto<Map<String, Object>> getUserById(@PathVariable Long id) {
        return ApiResponseDto.success(userService.getUserById(id));
    }

    @PostMapping(value = "/", consumes = "multipart/form-data")
    public ApiResponseDto<Long> saveUser(
            @RequestParam Map<String, Object> params,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        Long id = userService.saveUser(params, file);
        return ApiResponseDto.success(id);
    }

    @PostMapping(value = "/{id}", consumes = "multipart/form-data")
    public ApiResponseDto<Void> updateUser(
            @PathVariable Long id,
            @RequestParam Map<String, Object> params,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        params.put("id", id);
        userService.updateUser(params, file);
        return ApiResponseDto.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponseDto<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponseDto.success();
    }
}
