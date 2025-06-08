package com.example.Bank.Application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDebitRequest {
    @Schema(name="User Account Number")
    @NotBlank(message = "Account Number should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private String accountNumber;
    @Schema(name="Amount")
    @Positive(message = "Amount must be greater than zero")
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
