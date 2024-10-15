package com.assignment.FreightFox.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.FreightFox.Common.S3Service;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchFiles(@RequestParam String userName, @RequestParam String searchTerm) {
        List<String> files = s3Service.searchFiles(userName, searchTerm);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String userName, @RequestParam String fileName) {
        byte[] data = s3Service.downloadFile(userName, fileName);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam String userName, @RequestParam("file") MultipartFile file) {
        s3Service.uploadFile(userName, file);
        return ResponseEntity.ok("File uploaded successfully");
    }
}