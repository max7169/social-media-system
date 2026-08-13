package com.example.social_media_backend.controller;

import com.example.social_media_backend.dto.CreateCommentRequest;
import com.example.social_media_backend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.social_media_backend.model.Comment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.social_media_backend.security.CustomUserDetails;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

   @PostMapping("/{postId}/comments")
    public ResponseEntity<Map<String, Object>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getUserId();

        request.setUserId(userId);

        Long commentId =
                commentService.createComment(postId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "commentId", commentId,
                        "message", "Comment created successfully"
                ));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(
            @PathVariable Long postId) {

        List<Comment> comments =
                commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(comments);
    }
}