package com.example.Bank.Application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    @Schema(name = "User Email")
    @NotBlank(message = "Email should not be empty")
    private String email;
    @Schema(name = "User Password")
    @NotBlank(message = "Password should not be empty")
    private String password;
}
