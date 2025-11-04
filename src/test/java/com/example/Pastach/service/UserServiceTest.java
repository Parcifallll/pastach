package com.example.Pastach.service;

import com.example.Pastach.dto.mapper.UserMapper;
import com.example.Pastach.dto.user.UserCreateDTO;
import com.example.Pastach.dto.user.UserResponseDTO;
import com.example.Pastach.dto.user.UserUpdateDTO;
import com.example.Pastach.exception.UserAlreadyExistException;
import com.example.Pastach.exception.UserNotFoundException;
import com.example.Pastach.model.User;
import com.example.Pastach.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("when id and email are free, then create User")
    void create_WhenIdAndEmailFree_ThenCreate() {
        UserCreateDTO dto = new UserCreateDTO("newId", "new@domain.com", "New", LocalDate.of(2000, 1, 1));

        UserResponseDTO result = userService.create(dto);

        assertThat(result.id()).isEqualTo("newId");
        assertThat(result.email()).isEqualTo("new@domain.com");
        assertThat(userRepository.existsById("newId")).isTrue();
    }

    @Test
    @DisplayName("when user already exists by id, then throw UserAlreadyExistException")
    void create_WhenUserAlreadyExistsById_ThenThrowException() {
        UserCreateDTO dto = new UserCreateDTO("u1", "a@b.c", "A", null);
        userService.create(dto);

        UserCreateDTO duplicate = new UserCreateDTO("u1", "b@b.c", "B", null);

        UserAlreadyExistException e = assertThrows(UserAlreadyExistException.class, () -> userService.create(duplicate));
        assertThat(e.getMessage()).contains("id 'u1'");
    }

    @Test
    @DisplayName("when user is found by id, then return UserResponseDTO")
    void getById_WhenUserFound_ThenReturnUserResponseDTO() {
        userService.create(new UserCreateDTO("u1", "a@b.c", "A", null));

        UserResponseDTO result = userService.getById("u1");

        assertThat(result.id()).isEqualTo("u1");
    }

    @Test
    @DisplayName("when user is not found by id, then throw UserNotFoundException")
    void getById_WhenUserNotFound_ThenThrowUserNotFoundException() {
        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userService.getById("unknown"));
        assertThat(e.getMessage()).isEqualTo("User with id unknown not found");
    }

    @Test
    @DisplayName("when getAll is called, then return list of UserResponseDTO")
    void getAll_WhenCalled_ThenReturnListOfUserResponseDTO() {
        userService.create(new UserCreateDTO("u1", "a@b.c", "A", null));
        userService.create(new UserCreateDTO("u2", "b@b.c", "B", null));

        List<UserResponseDTO> result = userService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(UserResponseDTO::id).containsExactlyInAnyOrder("u1", "u2");
    }

    @Test
    @DisplayName("when update user, then fields are updated")
    void updateById_WhenUserFound_ThenUpdateFields() {
        userService.create(new UserCreateDTO("u1", "a@b.c", "Old", LocalDate.of(2000, 1, 1)));

        UserUpdateDTO update = new UserUpdateDTO("New", LocalDate.of(1990, 1, 1));

        UserResponseDTO result = userService.updateById("u1", update);

        assertThat(result.userName()).isEqualTo("New");
        assertThat(result.birthday()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("when deleteById is called, then user is deleted")
    void deleteById_WhenUserFound_ThenDeleteUser() {
        userService.create(new UserCreateDTO("u1", "a@b.c", "A", null));

        userService.deleteById("u1");

        assertThat(userRepository.findById("u1")).isEmpty();
    }
}