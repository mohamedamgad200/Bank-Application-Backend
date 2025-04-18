package com.example.Bank.Application.service.impl;

import com.example.Bank.Application.dto.BankResponse;
import com.example.Bank.Application.dto.CreditDebitRequest;
import com.example.Bank.Application.dto.EnquiryRequest;
import com.example.Bank.Application.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
}
