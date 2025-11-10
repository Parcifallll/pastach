package com.example.Pastach.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public record PostResponseDTO(
        Long id,
        String authorId,
        String text,
        String photoUrl,
        Instant createdAt) {
}
