package com.example.Pastach.dto.comment;


import lombok.Builder;

@Builder
public record CommentCreateDTO(
        String text,
        String photoUrl) {
}
