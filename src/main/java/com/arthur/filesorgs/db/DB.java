package com.arthur.filesorgs.db;

import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private static Connection connection = null;

    @Bean
    public static Connection getConnection() {
        try {
            System.out.println("jdbc url"+System.getenv("JDBC_URL"));
            connection = DriverManager.getConnection(System.getenv("JDBC_URL")+"?user=postgres&password=123123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if(connection!= null){
            connection.close();
        }
    }
}
