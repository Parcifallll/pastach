package com.example.Pastach.dto.comment;

import com.example.Pastach.model.Post;

import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder
public record CommentResponseDTO(Long id,
                                 String text,
                                 @JsonIgnore Post post,
                                 String photoUrl,
                                 String authorId, Instant createdAt) {
}
