package com.batch.service;

import com.batch.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Email mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail.getTo());
        msg.setFrom(mail.getFrom());
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getContent());
        System.out.println("\n msg " + msg.toString());
        javaMailSender.send(msg);
    }
}