package com.psionicgeek.demobankingapp.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {


    private UUID accountId;

    @NotNull(message = "User id is mandatory")
    private UUID userId;

    @NotNull(message = "Account number is mandatory")
    private String accountNumber;
    @NotNull(message = "Account name is mandatory")
    private String accountName;
    @NotNull(message = "Account type is mandatory")
    private String accountType;
    @NotNull(message = "Balance is mandatory")
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
