package com.example.Pastach.controller;


import com.example.Pastach.dto.user.UserCreateDTO;
import com.example.Pastach.dto.user.UserResponseDTO;
import com.example.Pastach.dto.user.UserUpdateDTO;
import com.example.Pastach.model.User;
import com.example.Pastach.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)  // 201
    @PostMapping
    public UserResponseDTO create(@Valid @RequestBody UserCreateDTO dto) {
        return userService.create(dto);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable String userId) {
        return userService.getById(userId);
    }

    @GetMapping
    public List<UserResponseDTO> getAll() {
        return userService.getAll();
    }

    @PatchMapping("/{userId}") // update info
    public UserResponseDTO update(@PathVariable String userId, @Valid @RequestBody UserUpdateDTO dto) {
        return userService.updateById(userId, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)  // 201
    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable String userId) {
        userService.deleteById(userId);
    }
}