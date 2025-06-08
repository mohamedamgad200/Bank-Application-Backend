package com.example.Bank.Application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnquiryRequest {
    @Schema(name="User Account Number")
    @NotBlank(message = "accountNumber should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private String accountNumber;
}
