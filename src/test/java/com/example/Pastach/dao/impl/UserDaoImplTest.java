package com.example.Pastach.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class UserDaoImplTest { // AAA method (Arrange, Act, Assert)
    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @BeforeEach


    @Test
    @DisplayName("findUserById: when user exists, should return the user") // BDD style: givenExistingUser_whenFindById_thenReturnUser()
    void findUserById_UserExists() {
    }

    @Test
    void findAll() {
    }

    @Test
    void updateById() {
    }

    @Test
    void create() {
    }

    @Test
    void deleteById() {
    }
}