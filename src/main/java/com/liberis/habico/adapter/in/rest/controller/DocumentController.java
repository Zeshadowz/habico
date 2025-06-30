package com.liberis.habico.adapter.in.rest.controller;

import com.liberis.habico.adapter.out.s3.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final S3Service s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload file to Storage",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data"
                    )
            )
    )
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            String keyName = file.getOriginalFilename();
            if (keyName == null || keyName.isEmpty()) {
                keyName = "untitled-file-" + System.currentTimeMillis(); // Fallback for files without original name
            }
            String result = s3Service.uploadFile(keyName, file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{keyName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String keyName) {
        try {
            byte[] data = s3Service.downloadFile(keyName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", keyName);
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Or a more specific error message
        }
    }

    @DeleteMapping("/delete/{keyName}")
    public ResponseEntity<String> deleteFile(@PathVariable String keyName) {
        String result = s3Service.deleteFile(keyName);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        List<String> fileNames = s3Service.listFiles();
        return ResponseEntity.ok(fileNames);
    }
}
