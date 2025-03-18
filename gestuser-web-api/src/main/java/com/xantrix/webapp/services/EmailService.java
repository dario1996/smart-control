package com.xantrix.webapp.services;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService 
{
	private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("dariobiffero@gmail.com", "Supporto Smart Control"); // 🌟 Personalizza il mittente
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true); // `true` abilita l'HTML

        mailSender.send(message);
    }
}
