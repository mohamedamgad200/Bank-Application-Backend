package com.example.Bank.Application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {
    @Schema(name="Response Code")
    private String responseCode;
    @Schema(name="Response Message")
    private String responseMessage;
    @Schema(name="User Account Information")
    private AccountInfo accountInfo;
}
