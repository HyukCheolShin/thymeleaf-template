package kr.co.aia.ipro.file.controller;

import kr.co.aia.ipro.common.dto.ApiResponseDto;
import kr.co.aia.ipro.file.service.FileService;
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
    public ApiResponseDto<Void> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("refTable") String refTable,
            @RequestParam("refId") Long refId) {

        fileService.uploadFile(file, refTable, refId);
        return ApiResponseDto.success();
    }

    @GetMapping("/")
    public ApiResponseDto<Object> getFiles(
            @RequestParam String refTable,
            @RequestParam Long refId) {
        return ApiResponseDto.success(fileService.getFiles(refTable, refId));
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
    public ApiResponseDto<Void> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ApiResponseDto.success();
    }
}
