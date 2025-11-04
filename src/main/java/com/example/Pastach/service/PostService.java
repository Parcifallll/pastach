package com.example.Pastach.service;

import com.example.Pastach.dto.post.PostCreateDTO;
import com.example.Pastach.dto.post.PostResponseDTO;
import com.example.Pastach.dto.post.PostUpdateDTO;
import com.example.Pastach.exception.PostNotFoundException;
import com.example.Pastach.exception.UserNotFoundException;
import com.example.Pastach.dto.mapper.PostMapper;
import com.example.Pastach.model.Post;
import com.example.Pastach.repository.PostRepository;
import com.example.Pastach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // generate constructor for final-fields
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;


    @Transactional
    public PostResponseDTO create(PostCreateDTO dto, String authorId) {
        if (!userRepository.existsById(authorId)) {
            throw new UserNotFoundException(authorId);
        }

        Post post = postMapper.toEntity(dto, authorId);
        post = postRepository.save(post); // create Post from dto
        return postMapper.toResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDTO getById(int postId) {
        return postRepository.findById(postId)
                .map(postMapper::toResponseDto) // map: Post -> PostResponseDto
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDTO> getAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return page.map(postMapper::toResponseDto);
    }

    // with pagination
    @Transactional(readOnly = true)
    public Page<PostResponseDTO> getByAuthorId(String authorId, Pageable pageable) {
        if (!userRepository.existsById(authorId)) {
            throw new UserNotFoundException(authorId);
        }

        Page<Post> page = postRepository.findByAuthorId(authorId, pageable);
        return page.map(postMapper::toResponseDto);
    }


    @Transactional
    public PostResponseDTO updateById(int postId, PostUpdateDTO dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        postMapper.updateFromDto(dto, post);
        post = postRepository.save(post);  // update existing post with new data from dto
        return postMapper.toResponseDto(post);
    }

    @Transactional
    public void deleteById(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        postRepository.delete(post);
    }

}