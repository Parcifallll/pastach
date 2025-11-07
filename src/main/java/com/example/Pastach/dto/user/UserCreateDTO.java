package com.example.Pastach.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record UserCreateDTO(
        @NotBlank
        String id,

        @NotBlank @Email
        String email,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        LocalDate birthday,

        boolean isLocked,

        @NotBlank @Size(min = 4)
        String password,
        Set<String> roles
) {
}