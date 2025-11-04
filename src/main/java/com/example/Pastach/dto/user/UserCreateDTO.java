package com.example.Pastach.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
public record UserCreateDTO(
        @NotBlank String id,
        @NotBlank @Email String email,
        String userName,
        LocalDate birthday
) {
}