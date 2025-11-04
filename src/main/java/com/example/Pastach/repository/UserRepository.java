package com.example.Pastach.repository;

import com.example.Pastach.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Do not write a realization: Spring Data JPA generates JPQL (entities -> Hibernate: entityManager parse JPQL into SQL
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // +basic auto-realizations on compiling
    boolean existsByEmail(String email);
}
