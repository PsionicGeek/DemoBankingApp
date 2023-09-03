package com.psionicgeek.demobankingapp.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    private UUID id;

    @NotBlank(message = "First name is mandatory")
    @NotNull(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @NotNull(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    @Pattern(regexp = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})", message = "Please Enter a Valid Email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    private String password;
    private String token;
    private String code;
    private int verified;
    private LocalDate verifiedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
