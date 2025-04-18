package com.example.Bank.Application.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnquiryRequest {
    @NotBlank(message = "accountNumber should not be empty")
    private String accountNumber;
}
