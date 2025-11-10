package com.example.Pastach.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE) // for JPA
@Entity
@Table(name = "posts", schema = "public")
@NoArgsConstructor // for JPA
public class Post {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @Column(name = "text")
    private String text;

    @Column(name = "photo_url")
    private String photoUrl;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // for PostService and PostMapper
    public Post(String text, String photoUrl, String authorId) {
        this.text = text;
        this.photoUrl = photoUrl;
        this.authorId = authorId;
    }
}