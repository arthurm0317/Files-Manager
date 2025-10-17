package com.arthur.filesorgs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class FilesOrganizationApplication {

	public static void main(String[] args) throws SQLException {

		SpringApplication.run(FilesOrganizationApplication.class, args);

	}

}
