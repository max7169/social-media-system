package com.example.social_media_backend.controller;

import com.example.social_media_backend.dto.CreateUserRequest;
import com.example.social_media_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.social_media_backend.dto.LoginRequest;
import com.example.social_media_backend.model.User;
import com.example.social_media_backend.security.CustomUserDetails;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @Valid @RequestBody CreateUserRequest request) {

        Long userId = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "userId", userId,
                        "message", "User registered successfully"
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginRequest request,
            jakarta.servlet.http.HttpServletRequest httpRequest) {

        User user = userService.login(
                request.getPhone(),
                request.getPassword()
        );

        var authentication =
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(user),
                        null,
                        new CustomUserDetails(user).getAuthorities()
                );

        var securityContext =
                org.springframework.security.core.context.SecurityContextHolder
                        .createEmptyContext();

        securityContext.setAuthentication(authentication);

        httpRequest.getSession(true).setAttribute(
                "SPRING_SECURITY_CONTEXT",
                securityContext
        );

        org.springframework.security.core.context.SecurityContextHolder
                .setContext(securityContext);

        return ResponseEntity.ok(
                Map.of(
                        "userId", user.getUserId(),
                        "userName", user.getUserName(),
                        "message", "Login successful"
                )
        );
    }
}