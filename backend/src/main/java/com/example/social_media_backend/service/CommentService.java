
package com.example.social_media_backend.service;

import com.example.social_media_backend.dto.CreateCommentRequest;
import com.example.social_media_backend.repository.CommentRepository;
import org.springframework.stereotype.Service;
import com.example.social_media_backend.model.Comment;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Long createComment(
            Long postId,
            CreateCommentRequest request) {

        return commentRepository.createComment(
                request.getUserId(),
                postId,
                request.getContent()
        );
    }

    public List<Comment> getCommentsByPostId(Long postId) {

        return commentRepository.getCommentsByPostId(postId);
    }

}