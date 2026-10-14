package com.udhaartracker.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class EmailService {

    @Value("${email.username}")
    private String username;

    @Value(("${email.password}"))
    private String password;

    public boolean sendEmail(String to,String from,String subject,String text){

        boolean flag = false;
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(text, "text/html;charset=utf-8");
            Transport.send(message);
            flag = true;
        }
        catch (Exception e) {
            log.error("Error sending email----{}",e.getMessage());
            System.out.println(e.getMessage());
        }

        return flag;
    }
}
