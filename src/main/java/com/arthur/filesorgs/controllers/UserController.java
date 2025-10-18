package com.arthur.filesorgs.controllers;

import com.arthur.filesorgs.entities.User;
import com.arthur.filesorgs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = service.createUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @PostMapping("login")
    public ResponseEntity<Optional<User>> login(@RequestBody User user){
        System.out.println(passwordEncoder.encode(user.getPassword()));
        Optional<User> searchUser = service.Login(user);
        if(searchUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(service.comparePassword(user.getPassword(), passwordEncoder.encode(user.getPassword()))) {
            return ResponseEntity.ok().body(searchUser);
        }else return null;
    }

}
