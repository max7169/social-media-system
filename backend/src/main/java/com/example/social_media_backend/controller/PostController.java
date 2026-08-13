package com.example.social_media_backend.controller;

import com.example.social_media_backend.model.Post;
import com.example.social_media_backend.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.social_media_backend.dto.CreatePostRequest;
import com.example.social_media_backend.dto.UpdatePostRequest;
import com.example.social_media_backend.dto.DeletePostRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.social_media_backend.security.CustomUserDetails;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(
            @Valid @RequestBody CreatePostRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getUserId();

        request.setUserId(userId);

        Long postId = postService.createPost(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "postId", postId,
                        "message", "Post created successfully"
                ));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody UpdatePostRequest request) {

        Integer affectedRows =
                postService.updatePost(postId, request);

        if (affectedRows == 0) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = Map.of(
                "postId", postId,
                "message", "Post updated successfully"
        );

        return ResponseEntity.ok(response);
    }

    // @DeleteMapping("/{postId}")
    // public ResponseEntity<Map<String, Object>> deletePost(
    //         @PathVariable Long postId,
    //         @RequestParam Long userId) {

    //     postService.deletePost(postId, userId);

    //     Map<String, Object> response = Map.of(
    //             "postId", postId,
    //             "message", "Post deleted successfully"
    //     );

    //     return ResponseEntity.ok(response);
    // }

    @DeleteMapping("/{postId}")
public ResponseEntity<Map<String, Object>> deletePost(
        @PathVariable Long postId,
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    Long userId = userDetails.getUserId();

    postService.deletePost(postId, userId);

    return ResponseEntity.ok(
            Map.of(
                    "message", "Post deleted successfully"
            )
    );
}
}