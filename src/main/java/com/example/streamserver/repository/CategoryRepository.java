package com.example.streamserver.repository;

import com.example.streamserver.entity.AppRole;
import com.example.streamserver.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryName(String categoryName);
    boolean existsByCategoryName(String categoryName);
}
