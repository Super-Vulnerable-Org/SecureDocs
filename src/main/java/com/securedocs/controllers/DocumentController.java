package com.securedocs.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class DocumentController {

    // Vulnerable to LFI
    @GetMapping("/v1/download")
    public String downloadDocumentV1(@RequestParam String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return content;
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    // Secure Version with sanitization
    @GetMapping("/v2/download")
    public String downloadDocumentV2(@RequestParam String filePath) {
        try {
            // Prevent access to parent directories
            if (filePath.contains("..") || filePath.contains("/") || filePath.contains("\\\\")) {
                return "Invalid file path.";
            }

            // Allow only whitelisted files from a secure directory
            String safeBaseDir = "/opt/securedocs/";
            String fullPath = safeBaseDir + filePath;

            if (!Files.exists(Paths.get(fullPath))) {
                return "File not found.";
            }

            String content = new String(Files.readAllBytes(Paths.get(fullPath)));
            return content;
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }
}
