package com.example.Pastach.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
public record UserResponseDTO(
        String id,
        String userName,
        String email,
        LocalDate birthday
) {
}