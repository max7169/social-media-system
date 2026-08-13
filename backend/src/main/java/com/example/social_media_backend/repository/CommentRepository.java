package com.example.social_media_backend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.social_media_backend.model.Comment;
import java.util.List;
@Repository
public class CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createComment(
            Long userId,
            Long postId,
            String content) {

        return jdbcTemplate.queryForObject(
                "EXEC sp_CreateComment ?, ?, ?",
                Long.class,
                userId,
                postId,
                content
        );
    }

    public List<Comment> getCommentsByPostId(Long postId) {

        return jdbcTemplate.query(
                "EXEC sp_GetCommentsByPostId ?",
                (rs, rowNum) -> {

                    Comment comment = new Comment();

                    comment.setCommentId(rs.getLong("CommentId"));
                    comment.setUserId(rs.getLong("UserId"));
                    comment.setUserName(rs.getString("UserName"));
                    comment.setPostId(rs.getLong("PostId"));
                    comment.setContent(rs.getString("Content"));

                    if (rs.getTimestamp("CreatedAt") != null) {
                        comment.setCreatedAt(
                                rs.getTimestamp("CreatedAt")
                                        .toLocalDateTime()
                        );
                    }

                    return comment;
                },
                postId
        );
    }
}