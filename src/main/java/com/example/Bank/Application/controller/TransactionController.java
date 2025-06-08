package com.example.Bank.Application.controller;

import com.example.Bank.Application.entity.Transaction;
import com.example.Bank.Application.service.BankStatement;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/bankStatement")
@RequiredArgsConstructor
public class TransactionController {
    private final BankStatement bankStatement;
    @GetMapping
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate) throws FileNotFoundException, DocumentException
    {
        return bankStatement.generateStatment(accountNumber, startDate, endDate);
    }
}
