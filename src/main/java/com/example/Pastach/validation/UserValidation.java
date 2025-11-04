package com.example.Pastach.validation;

import com.example.Pastach.exception.InvalidEmailException;
import com.example.Pastach.exception.UserAlreadyExistException;
import com.example.Pastach.exception.UserNotFoundException;
import com.example.Pastach.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserValidation {
    public static void validateEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new InvalidEmailException("Invalid email address: " + email);
        }
    }
}
