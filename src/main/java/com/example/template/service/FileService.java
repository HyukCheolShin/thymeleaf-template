package com.example.template.service;

import com.example.template.common.exception.ResourceNotFoundException;
import com.example.template.mapper.FileMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMapper fileMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Transactional
    public void uploadFile(@NonNull MultipartFile file, @NonNull String refTable, @NonNull Long refId) {
        String originalName = file.getOriginalFilename();
        String cleanedName = StringUtils.cleanPath(originalName != null ? originalName : "unknown");
        String originalFileName = cleanedName.isEmpty() ? "unknown" : cleanedName;

        if (originalFileName.contains("..")) {
            throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFileName);
        }

        String extension = StringUtils.getFilenameExtension(originalFileName);
        extension = extension != null ? extension : "";

        String saveName = UUID.randomUUID().toString() + (extension.isEmpty() ? "" : "." + extension);

        try {
            Path targetLocation = this.fileStorageLocation.resolve(saveName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("refTable", refTable);
            fileMap.put("refId", refId);
            fileMap.put("originalName", originalFileName);
            fileMap.put("saveName", saveName);
            fileMap.put("size", file.getSize());
            fileMap.put("extension", extension);
            fileMap.put("path", saveName);
            fileMap.put("createdBy", "system");

            fileMapper.insert(fileMap);

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + originalFileName + ". Please try again!", ex);
        }
    }

    public List<Map<String, Object>> getFiles(@NonNull String refTable, @NonNull Long refId) {
        Map<String, Object> params = new HashMap<>();
        params.put("refTable", refTable);
        params.put("refId", refId);
        return fileMapper.findByRef(params);
    }

    @Nullable
    public Map<String, Object> getFileMetadata(@NonNull Long id) {
        return fileMapper.findById(id);
    }

    @NonNull
    public Resource loadFileAsResource(@NonNull String saveName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(saveName).normalize();
            java.net.URI fileUri = filePath.toUri();
            Resource resource = new UrlResource(fileUri);
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found " + saveName);
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found " + saveName, ex);
        }
    }

    @Transactional
    public void deleteFile(@NonNull Long id) {
        Map<String, Object> fileMetadata = fileMapper.findById(id);
        if (fileMetadata != null) {
            String saveName = (String) fileMetadata.get("saveName");
            deletePhysicalFile(saveName);
            fileMapper.delete(id);
        }
    }

    @Transactional
    public void deleteFilesByRef(@NonNull String refTable, @NonNull Long refId) {
        List<Map<String, Object>> files = getFiles(refTable, refId);
        for (Map<String, Object> file : files) {
            String saveName = (String) file.get("saveName");
            deletePhysicalFile(saveName);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("refTable", refTable);
        params.put("refId", refId);
        fileMapper.deleteByRef(params);
    }

    private void deletePhysicalFile(String saveName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(saveName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            log.error("Could not delete physical file {}: {}", saveName, ex.getMessage());
        }
    }
}
