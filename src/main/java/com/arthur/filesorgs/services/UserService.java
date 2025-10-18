package com.arthur.filesorgs.services;

import com.arthur.filesorgs.db.DB;
import com.arthur.filesorgs.entities.User;
import com.arthur.filesorgs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class UserService {
    Connection connection = DB.getConnection();

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> findAll(){
        Statement st = null;
        try{
            st = connection.createStatement();
            ResultSet set = st.executeQuery("SELECT * FROM users");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return repository.findAll();
    }

    public User createUser(User user){
        User newUser = new User(user.getName(), user.getEmail(), user.getPassword());
        PreparedStatement st = null;
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            st = connection.prepareStatement(
                    "INSERT INTO users"+"(name, email, password, id)"+"VALUES"+"(?, ?, ?, ?)"
            );
            st.setString(1, newUser.getName());
            st.setString(2, newUser.getEmail());
            st.setString(3, newUser.getPassword());
            st.setString(4, UUID.randomUUID().toString());

            int rowsAffected = st.executeUpdate();
            System.out.println(rowsAffected);
            connection.close();
        }catch (SQLException e){
            System.out.println(e);
        }
        return newUser;
    }
    public Optional<User> Login(User user){
        User searchUser = new User(user.getName(), user.getEmail(), user.getPassword());
        PreparedStatement st = null;
        try{
            Optional<User> user1 = repository.findByEmail(searchUser.getEmail());
            if(user1!=null){
                return user1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean comparePassword(String password, String encodedPassword){
        return passwordEncoder.matches(password, encodedPassword);
    }
}
