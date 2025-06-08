package com.example.Bank.Application.service;

import com.example.Bank.Application.dto.TransactionDto;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
