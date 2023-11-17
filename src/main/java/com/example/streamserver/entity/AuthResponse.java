package com.example.streamserver.entity;

public class AuthResponse {
    private String content;
    public AuthResponse(){}

    public AuthResponse(String content){
        this.content = content;
    }
    public String getcontent() {
        return content;
    }
    public void setcontent(String content) {
        this.content = content;
    }
}
