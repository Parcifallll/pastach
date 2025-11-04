package com.example.Pastach.dto.post;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record PostCreateDTO(String text, String photoUrl) {
}

