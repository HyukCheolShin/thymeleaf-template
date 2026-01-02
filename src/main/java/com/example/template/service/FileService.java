package com.example.template.service;

import com.example.template.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;

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

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final Path fileStorageLocation;

    public FileService(FileMapper fileMapper, @Value("${file.upload-dir}") String uploadDir) {
        this.fileMapper = fileMapper;
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public void uploadFile(MultipartFile file, String refTable, Long refId) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";

        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            extension = originalFileName.substring(i + 1);
        }

        String saveName = UUID.randomUUID().toString() + "." + extension;

        try {
            // Check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(saveName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Save metadata to DB
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("refTable", refTable);
            fileMap.put("refId", refId);
            fileMap.put("originalName", originalFileName);
            fileMap.put("saveName", saveName);
            fileMap.put("size", file.getSize());
            fileMap.put("extension", extension);
            fileMap.put("path", targetLocation.toString());
            fileMap.put("createdBy", "system"); // TODO: Replace with actual user

            fileMapper.insert(fileMap);

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + originalFileName + ". Please try again!", ex);
        }
    }

    public List<Map<String, Object>> getFiles(String refTable, Long refId) {
        Map<String, Object> params = new HashMap<>();
        params.put("refTable", refTable);
        params.put("refId", refId);
        return fileMapper.findByRef(params);
    }

    public Map<String, Object> getFileMetadata(Long id) {
        return fileMapper.findById(id);
    }

    public Resource loadFileAsResource(String saveName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(saveName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + saveName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + saveName, ex);
        }
    }

    public void deleteFile(Long id) {
        Map<String, Object> fileMetadata = fileMapper.findById(id);
        if (fileMetadata != null) {
            String saveName = (String) fileMetadata.get("saveName");
            try {
                Path filePath = this.fileStorageLocation.resolve(saveName).normalize();
                Files.deleteIfExists(filePath);
            } catch (IOException ex) {
                throw new RuntimeException("Could not delete file " + saveName, ex);
            }
            fileMapper.delete(id);
        }
    }

    public void deleteFilesByRef(String refTable, Long refId) {
        List<Map<String, Object>> files = getFiles(refTable, refId);
        for (Map<String, Object> file : files) {
            String saveName = (String) file.get("saveName");
            try {
                Path filePath = this.fileStorageLocation.resolve(saveName).normalize();
                Files.deleteIfExists(filePath);
            } catch (IOException ex) {
                // Log warning but continue deleting other files
                System.err.println("Could not delete file " + saveName + ": " + ex.getMessage());
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("refTable", refTable);
        params.put("refId", refId);
        fileMapper.deleteByRef(params);
    }
}
