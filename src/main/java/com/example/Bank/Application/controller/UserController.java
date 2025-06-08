package com.example.Bank.Application.controller;

import com.example.Bank.Application.dto.*;
import com.example.Bank.Application.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.Table;
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
@Table(name = "User Account Management APIs")
public class UserController {
    private final UserService userService;

    @Operation(
            summary="Create New User Account",
            description = "Creating new user account and assigning and Account ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<BankResponse> createAccount(@Valid @RequestBody UserRequest userRequest)
    {
        BankResponse bankResponse=userService.createAccount(userRequest);
        return new ResponseEntity<>(bankResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary="Balance Enquiry",
            description = "Given an account number, check how much the user has"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http 200 SUCCESS"
    )
    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse>balanceEnquiry(@Valid @RequestBody EnquiryRequest enquiryRequest)
    {
        BankResponse bankResponse=userService.balanceEnquiry(enquiryRequest);
        return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }

    @Operation(
            summary="Name Enquiry",
            description = "Given an account number, check user name"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http 200 SUCCESS"
    )
    @GetMapping("/nameEnquiry")
    public ResponseEntity<Map<String,String>>nameEnquiry(@Valid @RequestBody EnquiryRequest enquiryRequest)
    {
        Map<String,String>response=new HashMap<>();
        String name=userService.nameEnquiry(enquiryRequest);
        response.put("Account Name",name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary="Credit Balance",
            description = "Given an account number and balance want to credit, add balance to account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http 200 SUCCESS"
    )
    @PostMapping("/credit")
    public ResponseEntity<BankResponse>creditAccount(@Valid @RequestBody CreditDebitRequest creditDebitRequest)
    {
       BankResponse bankResponse= userService.creditAccount(creditDebitRequest);
       return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }

    @Operation(
            summary="Debit Balance",
            description = "Given an account number and balance want to debit,Subtract balance from account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http 200 SUCCESS"
    )
    @PostMapping("/debit")
    public ResponseEntity<BankResponse>debitAccount(@Valid @RequestBody CreditDebitRequest creditDebitRequest)
    {
        BankResponse bankResponse=userService.debitAccount(creditDebitRequest);
        return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }

    @Operation(
            summary="Transfer balance",
            description = "Given an sender account number,receiver account number and balance want to transfer"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http 200 SUCCESS"
    )
    @PostMapping("/transfer")
    public ResponseEntity<BankResponse>transfer(@Valid @RequestBody TransferRequest transferRequest)
    {
        BankResponse bankResponse=userService.transfer(transferRequest);
        return new ResponseEntity<>(bankResponse, HttpStatus.OK);
    }
}
