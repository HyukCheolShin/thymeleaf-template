package com.example.template.controller;

import com.example.template.service.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FileApiController.class)
@WithMockUser
class FileApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileService fileService;

    @Test
    @DisplayName("파일 업로드 성공")
    void uploadFile() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes(StandardCharsets.UTF_8));

        // when & then
        mockMvc.perform(multipart("/api/files/upload")
                .file(file)
                .param("refTable", "users")
                .param("refId", "1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"));

        verify(fileService).uploadFile(any(), eq("users"), eq(1L));
    }

    @Test
    @DisplayName("파일 다운로드 성공")
    void downloadFile() throws Exception {
        // given
        Long fileId = 1L;
        String originalName = "test.txt";
        String saveName = "uuid-test.txt";

        Map<String, Object> metadata = Map.of(
                "id", fileId,
                "originalName", originalName,
                "saveName", saveName);

        Resource resource = new ByteArrayResource("Hello, World!".getBytes(StandardCharsets.UTF_8));

        given(fileService.getFileMetadata(fileId)).willReturn(metadata);
        given(fileService.loadFileAsResource(saveName)).willReturn(resource);

        // when & then
        mockMvc.perform(get("/api/files/download/{id}", fileId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"" + originalName + "\""))
                .andExpect(content().string("Hello, World!"));
    }

    @Test
    @DisplayName("파일 다운로드 실패 - 메타데이터 없음")
    void downloadFile_NotFound_Metadata() throws Exception {
        // given
        Long fileId = 999L;
        given(fileService.getFileMetadata(fileId)).willReturn(null);

        // when & then
        mockMvc.perform(get("/api/files/download/{id}", fileId))
                .andExpect(status().isNotFound());
    }
}
