package com.example.Pastach.repository;

import com.example.Pastach.model.Role;
import com.example.Pastach.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}