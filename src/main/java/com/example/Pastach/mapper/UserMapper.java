package com.example.Pastach.mapper;

import com.example.Pastach.dto.user.UserCreateDTO;
import com.example.Pastach.dto.user.UserResponseDTO;
import com.example.Pastach.dto.user.UserUpdateDTO;
import com.example.Pastach.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // DTo -> entity
    public User toEntity(UserCreateDTO dto) {
        return new User(
                dto.getId(),
                dto.getEmail(),
                dto.getUserName(),
                dto.getBirthday()
        );
    }

    // update: DTO -> entity
    public void updateFromDto(UserUpdateDTO dto, User user) {
        if (dto.getUserName() != null) {
            String name = dto.getUserName().trim();
            user.setUserName(name.isEmpty() ? "no_name" : name);
        }

        if (dto.getBirthday() != null) {
            user.setBirthday(dto.getBirthday());
        }
    }

    // response: entity -> DTO
    public UserResponseDTO toResponseDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setBirthday(user.getBirthday());
        return dto;
    }

}