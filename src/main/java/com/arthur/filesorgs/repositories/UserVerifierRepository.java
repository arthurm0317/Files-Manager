package com.arthur.filesorgs.repositories;

import com.arthur.filesorgs.entities.User;
import com.arthur.filesorgs.entities.UserVerifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserVerifierRepository extends JpaRepository<UserVerifier, Long> {
    UserVerifier findByUuid(UUID uuid);

}
