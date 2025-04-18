package com.example.Bank.Application.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
//    @NotBlank(message = "firstName should not be empty")
    private String firstName;
//    @NotBlank(message = "lastName should not be empty")
    private String lastName;
//    @NotBlank(message = "otherName should not be empty")
    private String otherName;
    @NotBlank(message = "gender should not be empty")
    private String gender;
    @NotBlank(message = "address should not be empty")
    private String address;
    @NotBlank(message = "stateOfOrigin should not be empty")
    private String stateOfOrigin;
    @NotBlank(message = "Email should not be empty")
    @Email(message = "Not Valid Email")
    private String email;
    @NotBlank(message = "phoneNumber should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String phoneNumber;
    @NotBlank(message = "alternativePhoneNumber should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String alternativePhoneNumber;
}
