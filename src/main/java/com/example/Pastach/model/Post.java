package com.example.Pastach.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Post { // Post-entity
    @Setter
    private int id;
    @NonNull
    private final String author;
    @Setter
    private String text;
    @Setter
    private String photoUrl;
    @Setter
    private LocalDateTime creationDate; // DB automatically fill the creation time

    public Post(String author, String text, String photoUrl) {
        this.author = author;
        this.text = text;
        this.photoUrl = photoUrl;
    }
}
