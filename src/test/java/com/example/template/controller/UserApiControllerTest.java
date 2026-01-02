package com.example.template.controller;

import com.example.template.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

import com.example.template.common.dto.PageRequest;
import com.example.template.common.dto.PageResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserApiController.class)
@WithMockUser
@SuppressWarnings("null")
class UserApiControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private UserService userService;

        @Test
        @DisplayName("전체 사용자 목록 조회 성공")
        void getAllUsers() throws Exception {
                // given
                Map<String, Object> user1 = Map.of(
                                "id", 1L,
                                "name", "홍길동",
                                "email", "user1@example.com",
                                "password", "password123",
                                "role", "USER",
                                "createdAt", "2025-01-01T12:00:00",
                                "updatedAt", "2025-01-01T12:00:00");

                PageResponse<Map<String, Object>> response = new PageResponse<>(
                                List.of(user1), 1, 10, 1);

                given(userService.getAllUsers(any(PageRequest.class))).willReturn(response);

                // when & then
                mockMvc.perform(get("/api/users/")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.code").value("SUCCESS")) // Check response code
                                .andExpect(jsonPath("$.data.list[0].id").value(1))
                                .andExpect(jsonPath("$.data.list[0].name").value("홍길동"))
                                .andExpect(jsonPath("$.data.totalItems").value(1))
                                .andExpect(jsonPath("$.data.totalPages").value(1))
                                .andExpect(jsonPath("$.data.currentPage").value(1));
        }

        @Test
        @DisplayName("사용자 검색 성공")
        void searchUsers() throws Exception {
                // given
                Map<String, Object> user1 = Map.of(
                                "id", 1L,
                                "name", "홍길동",
                                "email", "user1@example.com",
                                "password", "password123",
                                "role", "USER",
                                "createdAt", "2025-01-01T12:00:00",
                                "updatedAt", "2025-01-01T12:00:00");

                PageResponse<Map<String, Object>> response = new PageResponse<>(
                                List.of(user1), 1, 10, 1);

                given(userService.getAllUsers(any(PageRequest.class))).willReturn(response);

                // when & then
                mockMvc.perform(get("/api/users/")
                                .param("page", "1")
                                .param("size", "10")
                                .param("keyword", "홍길동")
                                .param("searchType", "name")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.code").value("SUCCESS")) // Check response code
                                .andExpect(jsonPath("$.data.list[0].id").value(1))
                                .andExpect(jsonPath("$.data.list[0].name").value("홍길동"));
        }

        @Test
        @DisplayName("ID로 사용자 조회 성공")
        void getUserById() throws Exception {
                // given
                Long id = 1L;
                Map<String, Object> user = Map.of(
                                "id", id,
                                "name", "홍길동",
                                "email", "user1@example.com",
                                "password", "password123",
                                "role", "USER",
                                "createdAt", "2025-01-01T12:00:00",
                                "updatedAt", "2025-01-01T12:00:00");
                given(userService.getUserById(id)).willReturn(user);

                // when & then
                mockMvc.perform(get("/api/users/{id}", id)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.code").value("SUCCESS")) // Check response code
                                .andExpect(jsonPath("$.data.id").value(1))
                                .andExpect(jsonPath("$.data.name").value("홍길동"))
                                .andExpect(jsonPath("$.data.email").value("user1@example.com"))
                                .andExpect(jsonPath("$.data.password").value("password123"))
                                .andExpect(jsonPath("$.data.role").value("USER"))
                                .andExpect(jsonPath("$.data.updatedAt").value("2025-01-01T12:00:00"))
                                .andExpect(jsonPath("$.data.createdAt").value("2025-01-01T12:00:00"));
        }

        @Test
        @DisplayName("사용자 등록 성공")
        void saveUser() throws Exception {
                // given
                // params setup removed as it is directly used in content string

                // when & then
                mockMvc.perform(post("/api/users/")
                                .with(csrf()) // Spring Security testing usually requires CSRF
                                              // token for POST
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                                "{\"email\":\"newuser@example.com\",\"password\":\"newpassword\",\"name\":\"New User\",\"role\":\"USER\"}"))
                                .andExpect(status().isOk());

                verify(userService).saveUser(any());
        }

        @Test
        @DisplayName("사용자 수정 성공")
        void updateUser() throws Exception {
                // when & then
                mockMvc.perform(put("/api/users/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("{\"email\":\"updated@example.com\",\"password\":\"newpassword\",\"name\":\"Updated User\",\"role\":\"ADMIN\"}"))
                                .andExpect(status().isOk());

                verify(userService).updateUser(any());
        }

        @Test
        @DisplayName("사용자 삭제 성공")
        void deleteUser() throws Exception {
                // when & then
                mockMvc.perform(delete("/api/users/1")
                                .with(csrf()))
                                .andExpect(status().isOk());

                verify(userService).deleteUser(eq(1L));
        }

        @Test
        @DisplayName("예외 발생 시 GlobalExceptionHandler 동작 확인 (ResourceNotFound)")
        void handleExceptionTest() throws Exception {
                // given
                given(userService.getUserById(999L))
                                .willThrow(new com.example.template.common.exception.ResourceNotFoundException(
                                                "User not found"));

                // when & then
                mockMvc.perform(get("/api/users/999")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                                .andExpect(jsonPath("$.message").value("User not found"));
        }

        @Test
        @DisplayName("잘못된 ID 형식 요청 시 400 에러 반환")
        void handleTypeMismatchTest() throws Exception {
                // when & then
                mockMvc.perform(get("/api/users/invalid-id")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                                .andExpect(jsonPath("$.message").value("Invalid value for parameter: id"));
        }

        @Test
        @DisplayName("필수 값 누락 시 400 에러 반환")
        void validationTest() throws Exception {
                // given
                Map<String, Object> params = new HashMap<>(); // Empty params
                willThrow(new IllegalArgumentException("Field 'name' is required")).given(userService)
                                .saveUser(any());

                // when & then
                mockMvc.perform(post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(params))
                                .with(csrf()))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                                .andExpect(jsonPath("$.message").value("Field 'name' is required"));
        }
}
