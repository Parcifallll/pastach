package com.example.Pastach.service;

import com.example.Pastach.dto.mapper.UserMapper;
import com.example.Pastach.exception.UserAlreadyExistException;
import com.example.Pastach.exception.UserNotFoundException;
import com.example.Pastach.model.User;
import com.example.Pastach.repository.UserRepository;
import com.example.Pastach.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Pastach.dto.user.UserCreateDTO;
import com.example.Pastach.dto.user.UserResponseDTO;
import com.example.Pastach.dto.user.UserUpdateDTO;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO create(UserCreateDTO dto) {
        if (userRepository.existsById(dto.getId())) {
            throw new UserAlreadyExistException("User with id " + dto.getId() + " already exists");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistException("User with email " + dto.getEmail() + " already exists");
        }

        User user = userRepository.save(userMapper.toEntity(dto, dto.getId()));
        return userMapper.toResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUser(String userId) {
        return userRepository.findById(userId)
                .map(userMapper::toResponseDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .toList();
    }


    @Transactional
    public UserResponseDTO updateById(String userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (userRepository.existsById(dto.getId())) {
            throw new UserAlreadyExistException("User with id " + dto.getId() + " already exists");
        }

        userMapper.updateFromDto(dto, user);
        user = userRepository.save(user); // updated existing user from dto
        return userMapper.toResponseDto(user);
    }


    @Transactional
    public void deleteById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userRepository.delete(user);
    }
}
