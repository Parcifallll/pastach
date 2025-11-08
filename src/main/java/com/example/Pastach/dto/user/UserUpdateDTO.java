package com.example.Pastach.dto.user;

import lombok.Builder;

import java.time.LocalDate;


@Builder
public record UserUpdateDTO(
        String firstName,
        String lastName,
        LocalDate birthday
) {
}
