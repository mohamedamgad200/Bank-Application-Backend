package com.example.Bank.Application.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnquiryRequest {
    @NotBlank(message = "accountNumber should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private String accountNumber;
}
