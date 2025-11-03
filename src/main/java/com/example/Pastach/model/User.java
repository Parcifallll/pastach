package com.example.Pastach.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // only for JPA
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(name = "user_name")
    private String userName = "no_name";

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    // for service
    public User(String id, String email, String userName, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.userName = (userName == null || userName.isBlank()) ? "no_name" : userName.trim();
        this.birthday = birthday;
    }
}