package com.example.Pastach;

import com.example.Pastach.model.Role;
import com.example.Pastach.model.RoleEnum;
import com.example.Pastach.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {

            if (roleRepository.findByName(RoleEnum.USER).isEmpty()) {
                Role userRole = new Role();
                userRole.setName(RoleEnum.USER);
                roleRepository.save(userRole);
            }

            if (roleRepository.findByName(RoleEnum.ADMIN).isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName(RoleEnum.ADMIN);
                roleRepository.save(adminRole);
            }

            if (roleRepository.findByName(RoleEnum.GUEST).isEmpty()) {
                Role guestRole = new Role();
                guestRole.setName(RoleEnum.GUEST);
                roleRepository.save(guestRole);
            }
        };
    }
}