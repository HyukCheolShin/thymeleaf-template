package com.example.template.controller;

import com.example.template.common.dto.ApiResponse;
import com.example.template.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileApiController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ApiResponse<Void> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("refTable") String refTable,
            @RequestParam("refId") Long refId) {

        fileService.uploadFile(file, refTable, refId);
        return ApiResponse.success();
    }

    @GetMapping("/")
    public ApiResponse<Object> getFiles(
            @RequestParam String refTable,
            @RequestParam Long refId) {
        return ApiResponse.success(fileService.getFiles(refTable, refId));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Map<String, Object> fileMetadata = fileService.getFileMetadata(id);

        if (fileMetadata == null) {
            return ResponseEntity.notFound().build();
        }

        String saveName = (String) fileMetadata.get("saveName");
        String originalName = (String) fileMetadata.get("originalName");

        Resource resource = fileService.loadFileAsResource(saveName);

        // Encode filename for browser compatibility
        String encodedUploadFileName = URLEncoder.encode(originalName, StandardCharsets.UTF_8).replace("+", "%20");
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ApiResponse.success();
    }
}
