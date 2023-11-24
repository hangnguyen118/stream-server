package com.example.streamserver.dto;

import com.example.streamserver.entity.Category;
import com.example.streamserver.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class VideoDto {
    private String title;
    private String sourceUri;
    private String preview;
    private String info;
    private long views;
    private long likes;
    private String quality;
    private String subtitle;
    private Time term;
    private LocalDateTime uploadTime;
    private String authorEmail;
    private String authorName;
    private Set<Category> categories;
}
