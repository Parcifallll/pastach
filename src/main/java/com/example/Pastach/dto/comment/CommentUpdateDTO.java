package com.example.Pastach.dto.comment;


import lombok.Builder;

@Builder
public record CommentUpdateDTO(
        String text,
        String photoUrl) {
}
