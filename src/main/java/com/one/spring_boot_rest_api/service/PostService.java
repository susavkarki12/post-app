package com.one.spring_boot_rest_api.service;

import com.one.spring_boot_rest_api.payload.PostDto;
import com.one.spring_boot_rest_api.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(Long id, PostDto postDto);
    void deletePost(Long id);
}
