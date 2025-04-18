package com.example.Bank.Application.utils;

import java.time.Year;

public class AccountUtils {
    public static final String ACCOUNT_EXISTS_CODE="001";
    public static final String ACCOUNT_EXISTS_MESSAGE="This user already has an account created!";
    public static final String ACCOUNT_CREATION_CODE="002";
    public static final String ACCOUNT_CREATION_MESSAGE="Account has been successfully created! ";
    public static final String ACCOUNT_NOT_EXISTS_CODE="003";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE="User With the provided Account Number does not exist! ";
    public static final String ACCOUNT_FOUND_CODE="004";
    public static final String ACCOUNT_FOUND_MESSAGE="User Account Found!";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account was credited successfully";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been successfully debited";
    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer has been successfully";
    public static String generateAccountNumber(){
        /**
         * 2025 + randomSixDigits
         */
        Year currentYear= Year.now();
        int min=100000;
        int max=999999;
        //generate random number between min and max
        int randNumber=(int)(Math.floor((Math.random()*(max-min+1)+min)));
        //convert current year and randomNumber to string then concatenate them
        String year=String.valueOf(currentYear);
        String randomNumber=String.valueOf(randNumber);
        StringBuilder accountNumber=new StringBuilder();
        return accountNumber.append(year).append(randomNumber).toString();
    }
}
