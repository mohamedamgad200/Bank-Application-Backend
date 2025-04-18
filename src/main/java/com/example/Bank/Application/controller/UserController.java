package com.example.Bank.Application.controller;

import com.example.Bank.Application.dto.BankResponse;
import com.example.Bank.Application.dto.UserRequest;
import com.example.Bank.Application.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<BankResponse> createAccount(@Valid @RequestBody UserRequest userRequest)
    {
        BankResponse bankResponse=userService.createAccount(userRequest);
        return ResponseEntity.ok(bankResponse);
    }
}
