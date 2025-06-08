package com.example.Bank.Application.repository;

import com.example.Bank.Application.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
List<Transaction>findAllByAccountNumberAndCreatedAtBetween(String accountNumber, LocalDateTime from, LocalDateTime to);
}
