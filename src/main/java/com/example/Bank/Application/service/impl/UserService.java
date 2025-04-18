package com.example.Bank.Application.service.impl;

import com.example.Bank.Application.dto.BankResponse;
import com.example.Bank.Application.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}
