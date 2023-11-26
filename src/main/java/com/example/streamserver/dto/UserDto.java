package com.example.streamserver.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String phone;

    public UserDto(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }
}
