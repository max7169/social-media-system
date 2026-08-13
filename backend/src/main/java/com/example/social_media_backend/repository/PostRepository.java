package com.example.social_media_backend.repository;

import com.example.social_media_backend.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 取得所有文章
    public List<Post> getAllPosts() {

        return jdbcTemplate.query(
                "EXEC sp_GetAllPosts",
                this::mapPost
        );
    }

    // 新增文章
    public Long createPost(
            Long userId,
            String content,
            String image) {

        return jdbcTemplate.queryForObject(
                "EXEC sp_CreatePost ?, ?, ?",
                Long.class,
                userId,
                content,
                image
        );
    }

    // 修改文章
    public Integer updatePost(
            Long postId,
            Long userId,
            String content,
            String image) {

        return jdbcTemplate.queryForObject(
                "EXEC sp_UpdatePost ?, ?, ?, ?",
                Integer.class,
                postId,
                userId,
                content,
                image
        );
    }

    // 刪除文章
    public void deletePost(Long postId, Long userId) {

        jdbcTemplate.update(
                "EXEC sp_DeletePost ?, ?",
                postId,
                userId
        );
    }

    // 將 SQL Server 查詢結果轉成 Post
    private Post mapPost(ResultSet rs, int rowNum) throws SQLException {

        Post post = new Post();

        post.setPostId(rs.getLong("PostID"));
        post.setUserId(rs.getLong("UserID"));
        post.setContent(rs.getString("Content"));
        post.setImage(rs.getString("Image"));

        if (rs.getTimestamp("CreatedAt") != null) {
            post.setCreatedAt(
                    rs.getTimestamp("CreatedAt").toLocalDateTime()
            );
        }

        return post;
    }
}