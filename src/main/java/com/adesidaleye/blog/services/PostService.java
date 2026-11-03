package com.adesidaleye.blog.services;

import com.adesidaleye.blog.domain.CreatePostRequest;
import com.adesidaleye.blog.domain.UpdatePostRequest;
import com.adesidaleye.blog.domain.entities.Post;
import com.adesidaleye.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);

    List<Post> getDraftPosts(User user);

    Post createPost(User user, CreatePostRequest createPostRequest);

    Post getPost(UUID id);

    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);

    void deletePost(UUID id);
}
