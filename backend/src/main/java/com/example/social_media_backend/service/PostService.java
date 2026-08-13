package com.example.social_media_backend.service;

import com.example.social_media_backend.dto.CreatePostRequest;
import com.example.social_media_backend.dto.UpdatePostRequest;
import com.example.social_media_backend.model.Post;
import com.example.social_media_backend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 取得所有文章
    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    // 新增文章
    public Long createPost(CreatePostRequest request) {

        return postRepository.createPost(
                request.getUserId(),
                request.getContent(),
                request.getImage()
        );
    }

    // 修改文章
    public Integer updatePost(Long postId, UpdatePostRequest request) {

        return postRepository.updatePost(
                postId,
                request.getUserId(),
                request.getContent(),
                request.getImage()
        );
    }

    // 刪除文章
    public void deletePost(Long postId, Long userId) {

        postRepository.deletePost(
                postId,
                userId
        );
    }
}