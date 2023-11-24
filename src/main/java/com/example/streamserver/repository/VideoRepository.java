package com.example.streamserver.repository;

import com.example.streamserver.entity.User;
import com.example.streamserver.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
    Optional<Video> findById(UUID videoId);
    Optional<List<Video>> findByTitle(String title);
    Boolean existsByTitle(String title);
}
