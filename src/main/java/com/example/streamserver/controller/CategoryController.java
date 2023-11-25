package com.example.streamserver.controller;

import com.example.streamserver.dto.CategoryDto;
import com.example.streamserver.entity.AuthResponse;
import com.example.streamserver.entity.Category;
import com.example.streamserver.repository.CategoryRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    @PostMapping("/add")
    public ResponseEntity<AuthResponse> addCategory(@RequestBody CategoryDto categoryDto, HttpServletResponse response){
        if(categoryRepository.existsByCategoryName(categoryDto.getCategoryName())){
            return ResponseEntity.badRequest().body(new AuthResponse("Category đã tồn tại!!"));
        }
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);

        return ResponseEntity.ok(new AuthResponse("Đã tạo role thành công!"));
    }
}
