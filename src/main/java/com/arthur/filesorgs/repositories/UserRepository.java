package com.arthur.filesorgs.repositories;

import com.arthur.filesorgs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByVerifyToken(String verifyToken);
}
