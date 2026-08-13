package com.example.social_media_backend.security;

import com.example.social_media_backend.model.User;
import com.example.social_media_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone)
            throws UsernameNotFoundException {

        try {

            User user = userRepository.getUserByPhone(phone);

            return new CustomUserDetails(user);

        } catch (Exception e) {

            throw new UsernameNotFoundException(
                    "User not found"
            );
        }
    }
}