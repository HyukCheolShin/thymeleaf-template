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

import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SampleApiController.class)
@WithMockUser
class SampleApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SampleService sampleService;

    @Test
    @DisplayName("전체 샘플 목록 조회 성공")
    void getAllSamples() throws Exception {
        // given
        Map<String, Object> sample1 = Map.of("id", 1L, "params", "test1");
        given(sampleService.getAllSamples()).willReturn(List.of(sample1));

        // when & then
        mockMvc.perform(get("/api/samples/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].params").value("test1"));
    }

    @Test
    @DisplayName("ID로 샘플 조회 성공")
    void getSampleById() throws Exception {
        // given
        Long id = 1L;
        Map<String, Object> sample = Map.of("id", id, "params", "test1");
        given(sampleService.getSampleById(id)).willReturn(sample);

        // when & then
        mockMvc.perform(get("/api/samples/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.params").value("test1"));
    }
}
