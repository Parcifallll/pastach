package com.example.Pastach.dto.mapper;

import com.example.Pastach.dto.user.UserCreateDTO;
import com.example.Pastach.dto.user.UserResponseDTO;
import com.example.Pastach.dto.user.UserUpdateDTO;
import com.example.Pastach.model.Role;
import com.example.Pastach.model.RoleEnum;
import com.example.Pastach.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// MapStruct automatically create realization on compiling
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    // create: DTO -> Entity
    // other fields will be mapped automatically (do not require default value + exist in DTO with the same name)
    @Mapping(target = "roles", expression = "java(mapRoles(userCreateDTO.roles()))")
    User toEntity(UserCreateDTO userCreateDTO, String author);

    // update: DTO -> Entity
    @Mapping(target = "roles", expression = "java(mapRoles(userUpdateDTO.roles()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    void updateFromDto(UserUpdateDTO userUpdateDTO, @MappingTarget User user);

    // response: entity -> dto
    @Mapping(target = "roles", expression = "java(mapRoleNames(user.getRoles()))")
    UserResponseDTO toResponseDto(User user);

    // Set<String> -> Set<Role>
    default Set<Role> mapRoles(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            RoleEnum roleEnum = RoleEnum.valueOf(roleName.toUpperCase()); // String -> RoleEnum
            Role role = new Role();
            role.setName(roleEnum);
            roles.add(role);
        }
        return roles;
    }

    // Set<Role> -> Set<String>
    default Set<String> mapRoleNames(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(role -> role.getName().toString()) // RoleEnum -> String
                .collect(Collectors.toSet());
    }
}