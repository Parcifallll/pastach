package com.example.Pastach.dto.post;

import lombok.Builder;

@Builder
public record PostUpdateDTO(String text, String photoUrl) {
}