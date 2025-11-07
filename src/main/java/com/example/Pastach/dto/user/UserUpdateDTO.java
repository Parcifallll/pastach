package com.example.Pastach.dto.user;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record UserUpdateDTO(
        String firstName,
        String lastName,
        LocalDate birthday,
        String password,
        Set<String> roles,
        boolean isLocked
) {
}
