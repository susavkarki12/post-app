package com.one.spring_boot_rest_api.service.Impl;

import com.one.spring_boot_rest_api.entity.Post;
import com.one.spring_boot_rest_api.exception.ResourceNotFound;
import com.one.spring_boot_rest_api.payload.PostDto;
import com.one.spring_boot_rest_api.payload.PostResponse;
import com.one.spring_boot_rest_api.repository.PostRepository;
import com.one.spring_boot_rest_api.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert Dto to Entity
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        //convert Entity to Dto

        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create pageable instance

        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content= listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse= new PostResponse();
        postResponse.setContents(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", id, "id"));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", id, "id"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost= postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", id, "id"));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;
    }
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }
}
