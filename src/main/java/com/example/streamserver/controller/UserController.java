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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/admin")
public class UserController {
    private final CategoryRepository categoryRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
//    private final AppRoleRepository appRoleRepository;
    private final VideoRepository videoRepository;
    private final VideoService videoService;
    @GetMapping("/getall")
    public ResponseEntity<List<Category>> getAllUser(){
        System.out.println("user goi api la ydanh sach");
        List<Category> category = categoryRepository.findAll();
        return ResponseEntity.ok(category);
    }

//    @GetMapping("/user1")
//    public ResponseEntity<List<AppRole>> getUser1(){
//        System.out.println("user goi api la ydanh sach");
//        List<AppRole> user = appRoleRepository.findAll();
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/getalluser")
    public ResponseEntity<List<User>> getalluser(){
        System.out.println("user goi api la ydanh sach");
        List<User> user = userRepository.findAll();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getallvideo")
    public ResponseEntity<List<VideoDto>> getallvideor(){
        System.out.println("user goi api la ydanh sach");
        List<VideoDto> user = videoService.getAllVideos();
        return ResponseEntity.ok(user);
    }
}
