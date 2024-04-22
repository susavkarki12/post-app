package com.one.spring_boot_rest_api.service.Impl;

import com.one.spring_boot_rest_api.entity.Comment;
import com.one.spring_boot_rest_api.entity.Post;
import com.one.spring_boot_rest_api.exception.ResourceNotFound;
import com.one.spring_boot_rest_api.payload.CommentDto;
import com.one.spring_boot_rest_api.payload.PostDto;
import com.one.spring_boot_rest_api.repository.CommentRepository;
import com.one.spring_boot_rest_api.repository.PostRepository;
import com.one.spring_boot_rest_api.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("post", postId, "id"));

        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    public CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
