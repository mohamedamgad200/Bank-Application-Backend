package com.example.Bank.Application.service.impl;

import com.example.Bank.Application.dto.*;
import com.example.Bank.Application.entity.User;
import com.example.Bank.Application.repository.UserRepository;
import com.example.Bank.Application.service.EmailService;
import com.example.Bank.Application.service.JwtService;
import com.example.Bank.Application.service.TransactionService;
import com.example.Bank.Application.service.UserService;
import com.example.Bank.Application.utils.AccountUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TransactionService transactionService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public BankResponse login(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        EmailDetails loginAlert=EmailDetails.builder()
                .subject("You're logged in")
                .recipient(loginDto.getEmail())
                .messageBody("You logged into your account .If you did not initiate this action,please contact customer support immediately.")
                .build();
        emailService.sendEmailAlert(loginAlert);
        return BankResponse.builder()
                .responseCode("Login Success")
                .responseMessage(token)
                .build();
    }

    @Transactional
    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        /**
         * Creating an account - saving a new user into the db
         * Check if user already has account or now
         */
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();
        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("congratulations! Your Account Has Been Successfully Created. \nYour Account Details: \n" +
                        "Account Name : " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName() + "\n"
                        + "AccountNumber : " + savedUser.getAccountNumber() + "\n")
                .build();
        emailService.sendEmailAlert(emailDetails);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(savedUser.getAccountNumber())
                        .accountBalance(savedUser.getAccountBalance())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        //check if the provided account  number  exists in the db
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(
                        AccountInfo.builder()
                                .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                                .accountNumber(foundUser.getAccountNumber())
                                .accountBalance(foundUser.getAccountBalance())
                                .build()
                )
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        //check if the provided account  number  exists in the db
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExists) {
            return AccountUtils.ACCOUNT_NOT_EXISTS_CODE;
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

    @Transactional
    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        //check if the provided account  number  exists in the db
        boolean isAccountExists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));
        userRepository.save(userToCredit);
        EmailDetails creditAlert=EmailDetails.builder()
                .subject("CREDIT ALERT")
                .recipient(userToCredit.getEmail())
                .messageBody("The sum of "+creditDebitRequest.getAmount()+" has been sent to your account Your current balance is "+userToCredit.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);
        //save transaction
        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(creditDebitRequest.getAccountNumber())
                .amount(creditDebitRequest.getAmount())
                .transactionType("CREDIT")
                .build();
        transactionService.saveTransaction(transactionDto);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(
                        AccountInfo.builder()
                                .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                                .accountNumber(userToCredit.getAccountNumber())
                                .accountBalance(userToCredit.getAccountBalance())
                                .build()
                )
                .build();
    }

    @Transactional
    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        //check if the provided account  number  exists in the db
        boolean isAccountExists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!isAccountExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        //check if the amount you intend to withdraw is not more than the current account balance
        User userToDebit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        if (userToDebit.getAccountBalance().compareTo(creditDebitRequest.getAmount()) < 0) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        } else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount()));
            userRepository.save(userToDebit);
            EmailDetails debitAlert=EmailDetails.builder()
                    .subject("DEBIT ALERT")
                    .recipient(userToDebit.getEmail())
                    .messageBody("The sum of "+creditDebitRequest.getAmount()+" has been deducted from you account! Your current balance is "+userToDebit.getAccountBalance())
                    .build();
            emailService.sendEmailAlert(debitAlert);
            //save transaction
            TransactionDto transactionDto = TransactionDto.builder()
                    .accountNumber(creditDebitRequest.getAccountNumber())
                    .amount(creditDebitRequest.getAmount())
                    .transactionType("DEBIT")
                    .build();
            transactionService.saveTransaction(transactionDto);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(
                            AccountInfo.builder()
                                    .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                                    .accountNumber(userToDebit.getAccountNumber())
                                    .accountBalance(userToDebit.getAccountBalance())
                                    .build()
                    )
                    .build();
        }
    }

    @Transactional
    @Override
    public BankResponse transfer(TransferRequest transferRequest) {
        //check if the provided account  number  exists in the db destination and source
        boolean isTwoAccountsExists=userRepository.existsByAccountNumber(transferRequest.getSourceAccountNumber())&&userRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());
        if (!isTwoAccountsExists) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        //check if the source balance have more than the amount
        User sourceAccountUser=userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        if(sourceAccountUser.getAccountBalance().compareTo(transferRequest.getAmount())<0)
        {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));
        userRepository.save(sourceAccountUser);
        String sourceAccountnName=sourceAccountUser.getFirstName()+" "+sourceAccountUser.getLastName()+" "+sourceAccountUser.getOtherName();
        EmailDetails debitAlert=EmailDetails.builder()
                .subject("DEBIT ALERT")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The sum of "+transferRequest.getAmount()+" has been deducted from you account! Your current balance is "+sourceAccountUser.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);
        //save transaction
        TransactionDto transactionSourceDto = TransactionDto.builder()
                .accountNumber(transferRequest.getSourceAccountNumber())
                .amount(transferRequest.getAmount())
                .transactionType("DEBIT")
                .build();
        transactionService.saveTransaction(transactionSourceDto);
        User destinationAccountUser=userRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));
        userRepository.save(destinationAccountUser);
        EmailDetails creditAlert=EmailDetails.builder()
                .subject("CREDIT ALERT")
                .recipient(destinationAccountUser.getEmail())
                .messageBody("The sum of "+transferRequest.getAmount()+" has been sent to your account from "+sourceAccountnName +"Your current balance is "+sourceAccountUser.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);
        //save transaction
        TransactionDto transactionDestinationDto = TransactionDto.builder()
                .accountNumber(transferRequest.getDestinationAccountNumber())
                .amount(transferRequest.getAmount())
                .transactionType("CREDIT")
                .build();
        transactionService.saveTransaction(transactionDestinationDto);
        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }

}
