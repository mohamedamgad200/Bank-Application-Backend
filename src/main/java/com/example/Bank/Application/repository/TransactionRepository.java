package com.example.Bank.Application.repository;

import com.example.Bank.Application.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
