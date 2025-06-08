package com.example.Bank.Application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name="User First Name")
    @NotBlank(message = "firstName should not be empty")
    private String firstName;
    @Schema(name="User Second Name")
    @NotBlank(message = "lastName should not be empty")
    private String lastName;
    @Schema(name="User Other Name")
    @NotBlank(message = "otherName should not be empty")
    private String otherName;
    @Schema(name="User Gender")
    @NotBlank(message = "gender should not be empty")
    private String gender;
    @Schema(name="User Address")
    @NotBlank(message = "address should not be empty")
    private String address;
    @Schema(name="User State Of Origin")
    @NotBlank(message = "stateOfOrigin should not be empty")
    private String stateOfOrigin;
    @Schema(name="User Email")
    @NotBlank(message = "Email should not be empty")
    @Email(message = "Not Valid Email")
    private String email;
    @Schema(name="User Mobile Number")
    @NotBlank(message = "phoneNumber should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String phoneNumber;
    @Schema(name="User Alternative Mobile Number")
    @NotBlank(message = "alternativePhoneNumber should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String alternativePhoneNumber;
}
