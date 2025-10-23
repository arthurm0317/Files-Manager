package com.arthur.filesorgs.services;

import jakarta.servlet.Servlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public String sendMail(String reciver, String subject, String message, String path, String token){
        try{
            String actionUrl = ServletUriComponentsBuilder.fromPath("http://localhost:5173").path(path).queryParam("token", token).toUriString();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(subject);
            mailMessage.setTo(reciver);
            mailMessage.setFrom(from);
            mailMessage.setText(message + "\n" + actionUrl);
            mailSender.send(mailMessage);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return "Email enviado";
    }
}
