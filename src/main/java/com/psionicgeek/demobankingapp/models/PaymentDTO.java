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
public class PaymentDTO {

    private UUID paymentId;
    @NotNull
    private UUID accountId;
    @NotNull
    private UUID userId;
    @NotNull
    private String beneficiary;
    @NotNull
    private String beneficiaryAccNo;
    @NotNull
    private BigDecimal amount;
    private String referenceNo;
    private String status;
    private String reasonCode;
    private LocalDateTime createdAt;
}
