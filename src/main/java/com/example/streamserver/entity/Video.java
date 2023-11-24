package com.example.streamserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="Video", uniqueConstraints = {@UniqueConstraint(name="VIDEO_UK", columnNames = "Title")})
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Video_Id", nullable = false)
    private UUID videoId;

    @Column(name="Title", nullable = false, length = 200)
    private String title;

    @Column(name="SourceUri", nullable = false)
    private String sourceUri;

    @Column(name="Preview", nullable = false)
    private String preview;

    @Column(name="Info", nullable = false, length = 600)
    private String info;

    @Column(name="Views", nullable = false)
    private long views;

    @Column(name="Likes", nullable = false)
    private long likes;

    @Column(name="Quality", nullable = false)
    private String quality;

    @Column(name="Subtitle")
    private String subtitle;

    @Column(name="Term")
    private Time term;

    @Column(name="UploadTime", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "video_category",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;
}
