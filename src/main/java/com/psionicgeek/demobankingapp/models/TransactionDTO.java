package com.psionicgeek.demobankingapp.models;


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
public class TransactionDTO {

    private UUID transactionId;
    private UUID accountId;
    private UUID userID;
    private String transactionType;
    private BigDecimal amount;
    private String source;
    private String status;
    private String reasonCode;
    private LocalDateTime createdAt;
}
