package com.arthur.filesorgs.repositories;

import com.arthur.filesorgs.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
