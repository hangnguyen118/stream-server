package com.example.streamserver.controller;

import com.example.streamserver.entity.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/live")
public class StreamController {
    @PostMapping("/video-upload")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestParam("video") MultipartFile videoFile, HttpServletResponse response) throws IOException {
        if (videoFile != null && !videoFile.isEmpty()) {
            System.out.println(videoFile);
            String uploadDirectory = "D:\\NetBeansProjects\\video";
            String fileName = videoFile.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            Files.copy(videoFile.getInputStream(), Paths.get(filePath));
            System.out.println("Tệp video đã được tải lên thành công.");
            System.out.println(fileName);
        } else {
            System.out.println("Không có tệp video được tải lên.");
        }
        AuthResponse res = new AuthResponse("Tải lên video thành công!");
        return ResponseEntity.ok(res);
    }
}
