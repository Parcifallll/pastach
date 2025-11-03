package com.example.Pastach.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UserCreateDTO { // with validation

    @NotBlank(message = "ID can't be blank")
    private String id;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email")
    private String email;

    private String userName;

    private LocalDate birthday;
}