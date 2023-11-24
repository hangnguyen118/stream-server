package com.example.streamserver.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="Category", uniqueConstraints = {@UniqueConstraint(name="CATEGORY_UK", columnNames = "Category_name")})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Category_Id", nullable = false)
    private UUID categoryId;

    @Column(name="Category_name", nullable = false, length = 50)
    private String categoryName;

    @Column(name="Description", nullable = false, length = 200)
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Video> videos;
}
