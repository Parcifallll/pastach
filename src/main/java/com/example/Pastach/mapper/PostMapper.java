package com.example.Pastach.mapper;

import com.example.Pastach.dto.post.*;
import com.example.Pastach.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    // createPost: DTO + authorId -> Post
    public Post toEntity(PostCreateDTO dto, String authorId) {
        if (authorId == null || authorId.isBlank()) {
            throw new IllegalArgumentException("author_id can't be null");
        }
        return new Post(dto.getText(), dto.getPhoto_url(), authorId);
    }

    // update: DTO + post
    public void updateFromDto(PostUpdateDTO dto, Post post) {
        if (dto.getText() != null) {
            post.setText(dto.getText().isBlank() ? null : dto.getText().trim());
        }
        if (dto.getPhotoUrl() != null) {
            post.setPhotoUrl(dto.getPhotoUrl().isBlank() ? null : dto.getPhotoUrl());
        }
    }
    // response: post -> DTO
    public PostResponseDTO toResponseDto(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setAuthor(post.getAuthor());
        dto.setText(post.getText());
        dto.setPhotoUrl(post.getPhotoUrl());
        dto.setCreationDate(post.getCreationDate());
        return dto;
    }
}