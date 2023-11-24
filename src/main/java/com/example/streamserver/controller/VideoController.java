package com.example.streamserver.controller;

import com.example.streamserver.dto.RoleDto;
import com.example.streamserver.dto.VideoDto;
import com.example.streamserver.entity.*;
import com.example.streamserver.repository.UserRepository;
import com.example.streamserver.repository.VideoRepository;
import com.example.streamserver.service.CustomUserDetailsService;
import com.example.streamserver.service.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class VideoController {
    private final VideoService videoService;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;

//    @GetMapping("/all")
//    public ResponseEntity<List<User>> getAllVideos() {
//        List<User> videos = userDetailsService.getAllUsers();
//        System.out.println(videos);
//        if (videos.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.ok(videos);
//        }
//    }
    @PostMapping("/add")
    public ResponseEntity<AuthResponse> addVideo(@RequestBody VideoDto videoDto, HttpServletResponse response){
        if(videoRepository.existsByTitle(videoDto.getTitle())){
            return ResponseEntity.badRequest().body(new AuthResponse("Tiêu đề video đã tồn tại!!"));
        }
        Optional<User> userOptional = userRepository.findByEmail(videoDto.getAuthorEmail());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            Video video = new Video();
            video.setTitle(videoDto.getTitle());
            video.setSourceUri(videoDto.getSourceUri());
            video.setPreview(videoDto.getPreview());
            video.setInfo(videoDto.getInfo());
            video.setViews(0);
            video.setLikes(0);
            video.setQuality(videoDto.getQuality());
            video.setSubtitle(videoDto.getSubtitle());
            video.setUploadTime(LocalDateTime.now());
            video.setAuthor(user);
            video.setCategories(videoDto.getCategories());
            videoRepository.save(video);
            return ResponseEntity.ok(new AuthResponse("Đã thêm video thành công!"));
        }else {
            return ResponseEntity.badRequest().body(new AuthResponse("Không tìm thấy người dùng với email đã cung cấp"));
        }
    }

//    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    @ResponseBody
//    public ResponseEntity<ClassPathResource> streamVideo(@RequestParam(name = "sourceuri") String sourceUri) {
//        ClassPathResource video = new ClassPathResource("videos\\"+sourceUri);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(video);
//    }
    @GetMapping("/v1")
    public ResponseEntity<FileSystemResource> getVideo(@RequestParam(name = "sourceuri") String sourceUri) {
        String filePath = "D:\\NetBeansProjects\\stream-server\\src\\main\\resources\\videos\\"+sourceUri;
        File file = new File(filePath);
        FileSystemResource resource = new FileSystemResource(file);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(resource);
    }
}
