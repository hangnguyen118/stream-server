package com.example.streamserver.service;

import com.example.streamserver.dto.VideoDto;
import com.example.streamserver.entity.Category;
import com.example.streamserver.entity.Video;
import com.example.streamserver.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    public Optional<Video> findById(UUID videoId) {
        return videoRepository.findById(videoId);
    }

    public List<VideoDto> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        List<VideoDto> videoDTOs = new ArrayList<>();

        for (Video video : videos) {
            VideoDto videoDto = new VideoDto();
            videoDto.setTitle(video.getTitle());
            videoDto.setSourceUri(video.getSourceUri());
            videoDto.setPreview(video.getPreview());
            videoDto.setInfo(video.getInfo());
            videoDto.setViews(video.getViews());
            videoDto.setLikes(video.getLikes());
            videoDto.setQuality(videoDto.getQuality());
            videoDto.setSubtitle(video.getSubtitle());
            videoDto.setTerm(video.getTerm());
            videoDto.setUploadTime(video.getUploadTime());
            videoDto.setAuthorEmail(video.getAuthor().getEmail());
            videoDto.setAuthorName(video.getAuthor().getUsername());

            videoDTOs.add(videoDto);
        }
        return videoDTOs;
    }
}
