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
public class TransferRequest {
    @Schema(name="Source User Account Number")
    @NotBlank(message = "Source Account number should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Source Account number must be 10 digits")
    private String sourceAccountNumber;
    @Schema(name="Destination User Account Number")
    @NotBlank(message = "Destination Account number should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Destination Account number must be 10 digits")
    private String destinationAccountNumber;
    @Schema(name="Amount")
    @Positive(message = "Amount must be greater than zero")
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
}
