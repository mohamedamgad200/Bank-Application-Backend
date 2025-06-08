package com.example.Bank.Application.service.impl;

import com.example.Bank.Application.dto.EmailDetails;
import com.example.Bank.Application.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

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

    @Async
    @Override
    public void sendEmailWithAttachment(EmailDetails emailDetails) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try{
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            mimeMessageHelper.setText(emailDetails.getMessageBody(), true);
            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            javaMailSender.send(mimeMessage);
            log.info("✅ Email has sent to user with email {} {}", emailDetails.getRecipient(),file.getFilename());
        }
        catch (MessagingException ex)
        {
            log.error("❌ Failed to send email to {}", emailDetails.getRecipient(), ex);
            throw new RuntimeException(ex);
        }
    }
}
