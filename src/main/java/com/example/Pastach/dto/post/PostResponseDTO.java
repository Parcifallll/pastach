package com.example.Pastach.dto.post;

import com.example.Pastach.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostResponseDTO(
        Long id,
        String authorId,
        String text,
        String photoUrl,
        Instant createdAt,
        List<Comment> comments) {
}
