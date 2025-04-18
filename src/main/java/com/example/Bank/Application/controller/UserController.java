package com.example.Bank.Application.controller;

import com.example.Bank.Application.dto.BankResponse;
import com.example.Bank.Application.dto.EnquiryRequest;
import com.example.Bank.Application.dto.UserRequest;
import com.example.Bank.Application.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<BankResponse> createAccount(@Valid @RequestBody UserRequest userRequest)
    {
        BankResponse bankResponse=userService.createAccount(userRequest);
        return new ResponseEntity<>(bankResponse, HttpStatus.CREATED);
    }
    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse>balanceEnquiry(@Valid @RequestBody EnquiryRequest enquiryRequest)
    {
        BankResponse bankResponse=userService.balanceEnquiry(enquiryRequest);
        return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }
    @GetMapping("/nameEnquiry")
    public ResponseEntity<Map<String,String>>nameEnquiry(@Valid @RequestBody EnquiryRequest enquiryRequest)
    {
        Map<String,String>response=new HashMap<>();
        String name=userService.nameEnquiry(enquiryRequest);
        response.put("Account Name",name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
