package com.example.Pastach.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "users", schema = "public")
public class User {
    @NonNull
    @NotBlank
    @Id
    private String id;

    @Column(name = "user_name")
    private String userName = "no_name";

    @NonNull
    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @JsonFormat(pattern = "dd.MM.yyyy") // we should add a dependency
    @Column(name = "birthday")
    private LocalDate birthday;
}

