package com.example.Pastach.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDTO {
    private String userName;
    private LocalDate birthday;
}
