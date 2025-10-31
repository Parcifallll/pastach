package com.example.Pastach.controller;


import com.example.Pastach.model.User;
import com.example.Pastach.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{login}")
    public User getUser(@PathVariable String login) {
        log.info("getUser: {}", login + log.getClass());
        return userService.findUserById(login);
    }

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("findAll" + log.getClass().getName());
        return userService.findAll();
    }

    @PutMapping("/users/{userId}") // update info
    public User update(@RequestBody User user, @PathVariable String userId) {
        return userService.updateById(user, userId);
    }

    @PostMapping("/user")
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @DeleteMapping("/users/{userId}")
    public Optional<User> deleteById(@PathVariable String userId) {
        return userService.deleteById(userId);
    }
}