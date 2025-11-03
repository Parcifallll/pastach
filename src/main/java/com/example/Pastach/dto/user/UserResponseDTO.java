package com.example.Pastach.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseDTO {
    private String id;
    private String userName;
    private String email;
    private LocalDate birthday;
}
