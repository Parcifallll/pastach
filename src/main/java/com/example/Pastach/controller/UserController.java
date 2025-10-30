package com.example.Pastach.controller;


import com.example.Pastach.model.User;
import com.example.Pastach.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;
    private final Logger log = Logger.getLogger(UserController.class.getName());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{login}")
    public User getUser(@PathVariable String login) {
        log.info("getUser: " + login);
        return userService.findUserById(login);
    }

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("findAll");
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