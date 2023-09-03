package com.psionicgeek.demobankingapp.services;

import com.psionicgeek.demobankingapp.models.PaymentDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentServices {
    public void createPayment(BigDecimal bigDecimal, UUID accountUUID, String beneficiary, String beneficiaryAccNo, String reference);

    public List<PaymentDTO> getAllPayments(UUID id);
}
