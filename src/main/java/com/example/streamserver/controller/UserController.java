package com.example.streamserver.controller;

import com.example.streamserver.dto.VideoDto;
import com.example.streamserver.entity.AppRole;
import com.example.streamserver.entity.Category;
import com.example.streamserver.entity.User;
import com.example.streamserver.entity.Video;
import com.example.streamserver.repository.CategoryRepository;
import com.example.streamserver.repository.UserRepository;
import com.example.streamserver.repository.VideoRepository;
import com.example.streamserver.service.CustomUserDetailsService;
import com.example.streamserver.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
public class UserController {
    private final CategoryRepository categoryRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final VideoService videoService;
    @GetMapping("/getall")
    public ResponseEntity<List<Category>> getAllUser(){
        System.out.println("user goi api la ydanh sach");
        List<Category> category = categoryRepository.findAll();
        return ResponseEntity.ok(category);
    }

    @GetMapping("/getalluser")
    public ResponseEntity<List<User>> getalluser(){
        System.out.println("user goi api lay danh sach");
        List<User> user = userRepository.findAll();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/userLogout")
    public ResponseEntity<String> logoutOk(){
        System.out.println("user đã đăng xuất");
        return ResponseEntity.ok("Đăng xuất thành công.");
    }
    @GetMapping("/images")
    public ResponseEntity<FileSystemResource> getImage(@RequestParam(name = "sourceuri") String sourceUri) {
        String filePath = "D:\\NetBeansProjects\\stream-server\\src\\main\\resources\\images\\"+sourceUri;
        File file = new File(filePath);
        FileSystemResource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpg");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("image/jpg"))
                .body(resource);
    }
}
