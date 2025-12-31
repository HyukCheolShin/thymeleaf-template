package com.example.template.controller;

import com.example.template.service.SampleService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SampleApiController.class)
@WithMockUser
@SuppressWarnings("null")
class SampleApiControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private SampleService sampleService;

        @Test
        @DisplayName("전체 샘플 목록 조회 성공")
        void getAllSamples() throws Exception {
                // given
                Map<String, Object> sample1 = Map.of(
                                "id", 1L,
                                "name", "홍길동",
                                "email", "user1@example.com",
                                "password", "password123",
                                "role", "USER",
                                "createdAt", "2025-01-01T12:00:00",
                                "updatedAt", "2025-01-01T12:00:00");

                PageResponse<Map<String, Object>> response = new PageResponse<>(
                                List.of(sample1), 1, 10, 1);

                given(sampleService.getAllSamples(any(PageRequest.class))).willReturn(response);

                // when & then
                mockMvc.perform(get("/api/samples/")
                                .param("page", "1")
                                .param("size", "10")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.list[0].id").value(1))
                                .andExpect(jsonPath("$.list[0].name").value("홍길동"))
                                .andExpect(jsonPath("$.totalItems").value(1))
                                .andExpect(jsonPath("$.totalPages").value(1))
                                .andExpect(jsonPath("$.currentPage").value(1));
        }

        @Test
        @DisplayName("샘플 검색 성공")
        void searchSamples() throws Exception {
                // given
                Map<String, Object> sample1 = Map.of(
                                "id", 1L,
                                "name", "홍길동",
                                "email", "user1@example.com",
                                "password", "password123",
                                "role", "USER",
                                "createdAt", "2025-01-01T12:00:00",
                                "updatedAt", "2025-01-01T12:00:00");

                PageResponse<Map<String, Object>> response = new PageResponse<>(
                                List.of(sample1), 1, 10, 1);

                given(sampleService.getAllSamples(any(PageRequest.class))).willReturn(response);

                // when & then
                mockMvc.perform(get("/api/samples/")
                                .param("page", "1")
                                .param("size", "10")
                                .param("keyword", "홍길동")
                                .param("searchType", "name")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.list[0].id").value(1))
                                .andExpect(jsonPath("$.list[0].name").value("홍길동"));
        }

        @Test
        @DisplayName("ID로 샘플 조회 성공")
        void getSampleById() throws Exception {
                // given
                Long id = 1L;
                Map<String, Object> sample = Map.of(
                                "id", id,
                                "name", "홍길동",
                                "email", "user1@example.com",
                                "password", "password123",
                                "role", "USER",
                                "createdAt", "2025-01-01T12:00:00",
                                "updatedAt", "2025-01-01T12:00:00");
                given(sampleService.getSampleById(id)).willReturn(sample);

                // when & then
                mockMvc.perform(get("/api/samples/{id}", id)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.name").value("홍길동"))
                                .andExpect(jsonPath("$.email").value("user1@example.com"))
                                .andExpect(jsonPath("$.password").value("password123"))
                                .andExpect(jsonPath("$.role").value("USER"))
                                .andExpect(jsonPath("$.updatedAt").value("2025-01-01T12:00:00"))
                                .andExpect(jsonPath("$.createdAt").value("2025-01-01T12:00:00"));
        }

        @Test
        @DisplayName("샘플 등록 성공")
        void saveSample() throws Exception {
                // given
                // params setup removed as it is directly used in content string

                // when & then
                mockMvc.perform(post("/api/samples/")
                                .with(csrf()) // Spring Security testing usually requires CSRF
                                              // token for POST
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(
                                                "{\"email\":\"newuser@example.com\",\"password\":\"newpassword\",\"name\":\"New User\",\"role\":\"USER\"}"))
                                .andExpect(status().isOk());

                verify(sampleService).saveSample(any());
        }

        @Test
        @DisplayName("샘플 수정 성공")
        void updateSample() throws Exception {
                // when & then
                mockMvc.perform(put("/api/samples/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("{\"email\":\"updated@example.com\",\"password\":\"newpassword\",\"name\":\"Updated User\",\"role\":\"ADMIN\"}"))
                                .andExpect(status().isOk());

                verify(sampleService).updateSample(any());
        }

        @Test
        @DisplayName("샘플 삭제 성공")
        void deleteSample() throws Exception {
                // when & then
                mockMvc.perform(delete("/api/samples/1")
                                .with(csrf()))
                                .andExpect(status().isOk());

                verify(sampleService).deleteSample(eq(1L));
        }
}
