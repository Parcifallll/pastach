package com.example.Pastach.service;

import com.example.Pastach.dto.mapper.UserMapper;
import com.example.Pastach.dto.post.PostCreateDTO;
import com.example.Pastach.dto.user.UserCreateDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional // rollback after each test
class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    void create() {
        UserCreateDTO dto = new UserCreateDTO("id", "email@.com", "username", LocalDate.of(2005, 12, 23));
    }

    @Test
    void getUser() {
    }

    @Test
    void getAll() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }
}