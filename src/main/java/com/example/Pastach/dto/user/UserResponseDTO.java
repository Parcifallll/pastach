package com.example.Pastach.dto.user;

import lombok.Builder;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
public record UserResponseDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthday,
        boolean isLocked,
        Set<String> roles
) {
}