package com.arthur.filesorgs.services;

import com.arthur.filesorgs.entities.User;
import com.arthur.filesorgs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User createUser(User user){
        User newUser = new User(user.getName(), user.getEmail(), user.getPassword());
        repository.save(user);
        return newUser;
    }
}
