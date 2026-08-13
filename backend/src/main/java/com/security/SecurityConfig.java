package com.example.social_media_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.IF_REQUIRED
                )
            )

           .authorizeHttpRequests(auth -> auth

            .requestMatchers(
                "/api/auth/register",
                "/api/auth/login"
            ).permitAll()

            // GET 查文章，不需要登入
            .requestMatchers(
                org.springframework.http.HttpMethod.GET,
                "/api/posts",
                "/api/posts/**"
            ).permitAll()

            // POST 發文，需要登入
            .requestMatchers(
                org.springframework.http.HttpMethod.POST,
                "/api/posts"
            ).authenticated()

            // PUT 修改文章，需要登入
            .requestMatchers(
                org.springframework.http.HttpMethod.PUT,
                "/api/posts/**"
            ).authenticated()

            // DELETE 刪除文章，需要登入
            .requestMatchers(
                org.springframework.http.HttpMethod.DELETE,
                "/api/posts/**"
            ).authenticated()

            // POST 留言，需要登入
            .requestMatchers(
                org.springframework.http.HttpMethod.POST,
                "/api/posts/*/comments"
            ).authenticated()

            .anyRequest().authenticated()
        )

            .formLogin(form -> form.disable())

            .httpBasic(basic -> basic.disable());

        return http.build();
    }

   
}