package com.one.spring_boot_rest_api.service;

import com.one.spring_boot_rest_api.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
