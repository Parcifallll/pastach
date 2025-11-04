package com.example.Pastach.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User { // no constructors -> MapStruct create object automatically
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "username")
    private String userName = "no_name";

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;
}