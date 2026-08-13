package com.example.social_media_backend.service;

import com.example.social_media_backend.dto.CreateUserRequest;
import com.example.social_media_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.social_media_backend.model.User;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long createUser(CreateUserRequest request) {
        String passwordHash =
                passwordEncoder.encode(request.getPassword());

        return userRepository.createUser(
                request.getUserName(),
                request.getPhone(),
                request.getEmail(),
                passwordHash,
                request.getCoverImage(),
                request.getBiography()
        );
    }

    public User login(String phone, String password) {

        User user;

        try {
            user = userRepository.getUserByPhone(phone);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(
                    "Invalid phone or password"
            );
        }

        if (!passwordEncoder.matches(
                password,
                user.getPasswordHash())) {

            throw new IllegalArgumentException(
                    "Invalid phone or password"
            );
        }

        return user;
    }
}