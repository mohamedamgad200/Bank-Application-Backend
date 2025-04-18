package com.example.Bank.Application.service.impl;

import com.example.Bank.Application.dto.EmailDetails;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Async
    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try
        {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMessageBody());
            log.info("📨 Sending email to {}", emailDetails.getRecipient());
            javaMailSender.send(mailMessage);
            log.info("✅ Email sent successfully to {}", emailDetails.getRecipient());
        }catch (MailException ex)
        {
            log.error("❌ Failed to send email to {}", emailDetails.getRecipient(), ex);
            throw new RuntimeException(ex);
        }

    }
}
