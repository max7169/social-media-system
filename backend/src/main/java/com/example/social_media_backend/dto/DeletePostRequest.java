package com.example.social_media_backend.dto;

import jakarta.validation.constraints.NotNull;

public class DeletePostRequest {

    
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}