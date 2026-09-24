package com.example.social_media_backend.dto;

public class UserProfileResponse {

    private Long userId;
    private String userName;
    private String phone;
    private String email;
    private String coverImage;
    private String biography;

    public UserProfileResponse(
            Long userId,
            String userName,
            String phone,
            String email,
            String coverImage,
            String biography) {

        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.coverImage = coverImage;
        this.biography = biography;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getBiography() {
        return biography;
    }
}
