package com.example.Pastach.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDTO {
    private int id;
    private String author;
    private String text;
    private String photoUrl;
    private LocalDateTime creationDate;

}
