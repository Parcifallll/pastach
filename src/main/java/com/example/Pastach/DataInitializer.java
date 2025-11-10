package com.example.Pastach;

import com.example.Pastach.model.Role;
import com.example.Pastach.model.RoleEnum;
import com.example.Pastach.model.User;
import com.example.Pastach.repository.RoleRepository;
import com.example.Pastach.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository, UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
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

            if (userRepository.findById("admin").isEmpty()) {
                User admin = new User();
                admin.setId("admin");
                admin.setEmail("admin@example.com");
                admin.setFirstName("admin");
                admin.setLastName("admin");
                admin.setBirthday(LocalDate.of(1990, 1, 1));
                admin.setPassword(passwordEncoder.encode("adminpass123"));  // hash
                admin.setLocked(false);
                admin = userRepository.save(admin);

                Role adminRole = roleRepository.findByName(RoleEnum.ADMIN).orElseThrow();
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }
        };
    }
}