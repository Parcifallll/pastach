package com.example.Pastach.dto.mapper;

import com.example.Pastach.dto.user.UserCreateDTO;
import com.example.Pastach.dto.user.UserResponseDTO;
import com.example.Pastach.dto.user.UserUpdateDTO;
import com.example.Pastach.model.User;
import org.mapstruct.*;

// MapStruct automatically create realization on compiling
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    // create: DTO -> Entity
    // other fields will be mapped automatically (do not require default value + exist in DTO with the same name)
    @Mapping(target = "userName", source = "userCreateDTO.userName", defaultValue = "no_name")
    User toEntity(UserCreateDTO userCreateDTO, String author);

    // update: DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    void updateFromDto(UserUpdateDTO dto, @MappingTarget User user);

    // response: entity -> dto
    UserResponseDTO toResponseDto(User user);

}