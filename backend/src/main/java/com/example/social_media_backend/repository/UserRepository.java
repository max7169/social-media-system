package com.example.social_media_backend.repository;

import com.example.social_media_backend.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 註冊使用者
    public Long createUser(
            String userName,
            String phone,
            String email,
            String passwordHash,
            String coverImage,
            String biography) {

        return jdbcTemplate.queryForObject(
                "EXEC sp_CreateUser ?, ?, ?, ?, ?, ?",
                Long.class,
                userName,
                phone,
                email,
                passwordHash,
                coverImage,
                biography
        );
    }

    // 根據手機號碼取得使用者
    public User getUserByPhone(String phone) {

        return jdbcTemplate.queryForObject(
                "EXEC sp_GetUserByPhone ?",
                (rs, rowNum) -> {

                    User user = new User();

                    user.setUserId(rs.getLong("UserId"));
                    user.setUserName(rs.getString("UserName"));
                    user.setPhone(rs.getString("Phone"));
                    user.setEmail(rs.getString("Email"));
                    user.setPasswordHash(rs.getString("PasswordHash"));
                    user.setCoverImage(rs.getString("CoverImage"));
                    user.setBiography(rs.getString("Biography"));

                    return user;
                },
                phone
        );
    }
}