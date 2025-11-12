package com.example.Pastach.service;

import com.example.Pastach.dto.mapper.PostMapper;
import com.example.Pastach.dto.post.PostCreateDTO;
import com.example.Pastach.dto.post.PostResponseDTO;
import com.example.Pastach.dto.post.PostUpdateDTO;
import com.example.Pastach.exception.PostNotFoundException;
import com.example.Pastach.exception.UserNotFoundException;
import com.example.Pastach.model.Post;
import com.example.Pastach.model.RoleEnum;
import com.example.Pastach.model.User;
import com.example.Pastach.repository.PostRepository;
import com.example.Pastach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // generate constructor for final-fields
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;


    @PreAuthorize("isAuthenticated()")
    @Transactional
    public PostResponseDTO create(PostCreateDTO dto, String authorId) {
        if (!dto.hasContent()) throw new IllegalArgumentException("Post can't be empty");
        Post post = postMapper.toEntity(dto);
        post.setAuthorId(authorId);
        post = postRepository.save(post); // create Post from dto
        return postMapper.toResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDTO getById(Long postId) {
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


    @PreAuthorize("isAuthenticated()") // @PreAuthorize("authenticated()") + @AuthenticationPrincipal ... !
    @Transactional
    public PostResponseDTO updateById(Long postId, PostUpdateDTO dto, @AuthenticationPrincipal User curUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (!dto.hasContent()) throw new IllegalArgumentException("Post can't be empty");
        if (!post.getAuthorId().equals(curUser.getId())) {
            throw new AccessDeniedException("You are not authorized to edit this post.");
        }

        postMapper.updateFromDto(dto, post);
        post = postRepository.save(post);  // update existing post with new data from dto
        return postMapper.toResponseDto(post);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public void deleteById(Long postId, @AuthenticationPrincipal User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        boolean isAuthor = post.getAuthorId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().stream().anyMatch(r -> r.getName() == RoleEnum.ADMIN);
        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("You are not authorized to delete this post.");
        }
        postRepository.delete(post);
    }

}