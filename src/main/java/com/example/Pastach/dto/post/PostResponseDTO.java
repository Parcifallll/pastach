package com.example.Pastach.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
public record PostResponseDTO(
        int id,
        String authorId,
        String text,
        String photoUrl,
        LocalDateTime creationDate) {
}
