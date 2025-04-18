package com.example.Bank.Application.service.impl;

import com.example.Bank.Application.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
