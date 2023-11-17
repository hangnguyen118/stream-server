package com.example.streamserver.controller;

import com.example.streamserver.entity.User;
import com.example.streamserver.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> list = customUserDetailsService.getAllUsers();
        return ResponseEntity.ok(list);
    }
}
