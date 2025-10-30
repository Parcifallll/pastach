package com.example.Pastach.dao.impl;

import com.example.Pastach.dao.UserDao;
import com.example.Pastach.dao.mappers.UserRowMapper;
import com.example.Pastach.exception.UserNotFoundException;
import com.example.Pastach.model.Post;
import com.example.Pastach.model.User;
import com.example.Pastach.service.UserService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {
    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserDaoImpl(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public Optional<User> findUserById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        log.info("sql request sent");
        try {
            log.info(jdbcTemplate.getDataSource().toString());
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, id);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = jdbcTemplate.query(sql, userRowMapper);
        return users;
    }

    @Override
    public User updateById(User user, String userId) {
        String sql = "UPDATE users SET id = ?, username = ?, email = ?, birthday = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                Date.valueOf(user.getBirthday()), // java.sql.Date
                userId
        );

        if (rowsAffected == 0) {
            throw new RuntimeException("User with id " + userId + " not found for update");
        }

        user.setId(user.getId());
        return user;
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO users(id, username, email, birthday) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                Date.valueOf(user.getBirthday())); //java.sql.Date

        return user;
    }

    @Override
    public Optional<User> deleteById(String userId) {
        String selectSql = "SELECT * FROM users WHERE id = ?";

        Optional<User> user = jdbcTemplate.query(selectSql, userRowMapper, userId)
                .stream()
                .findFirst();

        if (user.isPresent()) {
            String deleteSql = "DELETE FROM users WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(deleteSql, userId);

            if (rowsAffected > 0) {
                return user;
            }
        }

        return Optional.empty();

    }
}
