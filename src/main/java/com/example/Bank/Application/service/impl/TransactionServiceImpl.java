package com.example.Bank.Application.service.impl;

import com.example.Bank.Application.dto.TransactionDto;
import com.example.Bank.Application.entity.Transaction;
import com.example.Bank.Application.repository.TransactionRepository;
import com.example.Bank.Application.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction= Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .amount(transactionDto.getAmount())
                .accountNumber(transactionDto.getAccountNumber())
                .status("Success")
                .build();
        transactionRepository.save(transaction);
        log.info("Transaction saved successfully");
    }
}
