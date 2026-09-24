package com.example.social_media_backend.controller;

import com.example.social_media_backend.dto.UserProfileResponse;
import com.example.social_media_backend.model.User;
import com.example.social_media_backend.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(
            Authentication authentication) {

        String phone = authentication.getName();

        User user = userService.getCurrentUser(phone);

        UserProfileResponse response = new UserProfileResponse(
                user.getUserId(),
                user.getUserName(),
                user.getPhone(),
                user.getEmail(),
                user.getCoverImage(),
                user.getBiography()
        );

        return ResponseEntity.ok(response);
    }
}
