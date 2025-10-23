package com.arthur.filesorgs.controllers;

import com.arthur.filesorgs.entities.User;
import com.arthur.filesorgs.entities.UserVerifier;
import com.arthur.filesorgs.entities.enums.UserStatus;
import com.arthur.filesorgs.repositories.UserVerifierRepository;
import com.arthur.filesorgs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value = "/users")
public class UserController {

    //Bcrypt sempre precisa do autowired aqui, senão dá erro (pesquisar o motivo depois, mas provavelmente é por nao inicializar junto ja que nao faz parte de um construtor)
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserVerifierRepository userVerifierRepository;

    @Autowired
    private UserService service;

    @GetMapping("/get-all")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User existingUser = service.findByEmail(user.getEmail());
        //Se existe, retorna um erro para evitar duplicação ou SQLException
        if(existingUser!=null) return ResponseEntity.status(400).build();
        User newUser = service.createUser(user);
        //A Uri serve para passarmos o caminho onde encontrará o novo usuario cadastrado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<User>> login(@RequestBody User user){
        Optional<User> searchUser = service.Login(user);
        //If para caso nao encontre o usuario
        if(searchUser.isEmpty()) return ResponseEntity.notFound().build();
        //Se pendente, retorna 401 para a request, forçando a confirmar email
        if(searchUser.get().getStatus().getCodigo().equals("P")) return ResponseEntity.status(401).build();
        //comparando a senha com a criptografada no banco
        if(service.comparePassword(searchUser.get().getPassword(), passwordEncoder.encode(user.getPassword()))) {
            return ResponseEntity.ok().body(searchUser);
        }else return null;
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String token){
        UserVerifier searchUser = service.compareToken(token);
        if(searchUser==null)return ResponseEntity.notFound().build();
        searchUser.getUser().setStatus(UserStatus.ATIVO);
        service.changeUserStatus(searchUser.getUser(), "A");
        userVerifierRepository.deleteById(searchUser.getId());
        return new ResponseEntity<>("Email verification was successful", HttpStatus.OK);
    }

}
