/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.streamserver.repository;

import com.example.streamserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author ntdie
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Optional<User> findById(UUID userId);

}
