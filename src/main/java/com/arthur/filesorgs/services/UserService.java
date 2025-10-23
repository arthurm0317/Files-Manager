package com.arthur.filesorgs.services;

import com.arthur.filesorgs.db.DB;
import com.arthur.filesorgs.entities.User;
import com.arthur.filesorgs.entities.UserVerifier;
import com.arthur.filesorgs.entities.enums.UserStatus;
import com.arthur.filesorgs.repositories.UserRepository;
import com.arthur.filesorgs.repositories.UserVerifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class UserService {
    Connection connection = DB.getConnection();

    @Autowired
    private UserVerifierRepository userVerifierRepository;

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

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

    public User findByEmail(String email){
        Optional<User> search = repository.findByEmail(email);
        if(search.isEmpty())return null;
        return search.get();
    }

    public User findByToken(String token){
        Optional<User> search = repository.findByVerifyToken(token);
        if(search.isEmpty())return null;
        if(search.get().getVerifyToken().equals(token)) return search.get();
        return null;
    }

    public User createUser(User user){
        //User
        User newUser = new User(user.getName(), user.getEmail(), passwordEncoder.encode(user.getPassword()));
        newUser.setStatus(UserStatus.PENDENTE);
        newUser.setId(null);
        //Setando um UUID como token de verificação, esse token vai aparecer no parametro da URL
        newUser.setVerifyToken(UUID.randomUUID().toString());

        //User verifier
        UserVerifier verifier = new UserVerifier();
        verifier.setUser(newUser);
        verifier.setUuid(UUID.fromString(newUser.getVerifyToken()));
        verifier.setData_expiration(Instant.now().plusMillis(1000*60*15));

        //Utilizando os services
        emailService.sendMail(user.getEmail(), "verify your email", "Click on this url to verify your email", "/verify", newUser.getVerifyToken());
        repository.save(newUser);
        userVerifierRepository.save(verifier);
        return newUser;
    }
    public Optional<User> Login(User user){
        User searchUser = new User(user.getName(), user.getEmail(), user.getPassword());
        PreparedStatement st = null;
        try{
            Optional<User> user1 = repository.findByEmail(searchUser.getEmail());
            if(user1.isPresent()){
                return user1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public boolean comparePassword(String password, String encodedPassword){
        return passwordEncoder.matches(password, encodedPassword);
    }

    //Verifica se está dentro do horario e verifica se o token está correto
    public UserVerifier compareToken(String token){
        UserVerifier searchUser = userVerifierRepository.findByUuid(UUID.fromString(token));
        if(searchUser==null)return null;
        if(Instant.now().isAfter(searchUser.getData_expiration()))return null;
        if(!searchUser.getUuid().equals(UUID.fromString(token)))return null;
        return searchUser;
    }


    //Função para ativar o status do usuario, futuramente ver se é possível passar como parametro o codigo, e assim pegar o status
    public void changeUserStatus(User user, String codigo){
        PreparedStatement st = null;
        try{

            st = connection.prepareStatement("UPDATE users SET status=? WHERE id=?");
            st.setString(1, UserStatus.status(codigo).toString());
            st.setString(2, user.getId());
            st.executeUpdate();
            st.close();
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
