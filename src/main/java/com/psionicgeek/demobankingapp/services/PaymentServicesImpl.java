package com.psionicgeek.demobankingapp.services;

import com.psionicgeek.demobankingapp.entities.Account;
import com.psionicgeek.demobankingapp.entities.Payment;
import com.psionicgeek.demobankingapp.mappers.PaymentMapper;
import com.psionicgeek.demobankingapp.models.PaymentDTO;
import com.psionicgeek.demobankingapp.repositories.AccountRepository;
import com.psionicgeek.demobankingapp.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class PaymentServicesImpl implements PaymentServices{

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    @Override
    public void createPayment(BigDecimal amount, UUID accountUUID, String beneficiary, String beneficiaryAccNo, String reference) {

            Account fromAccount = accountRepository.findByAccountId(accountUUID);

        Account toAccount = accountRepository.findByAccountNumberAndAccountName(beneficiaryAccNo, beneficiary);
        if (fromAccount == null || toAccount == null){
            Payment payment = Payment.builder()
                    .amount(amount)
                    .accountId(accountUUID)
                    .beneficiaryAccNo(beneficiaryAccNo)
                    .beneficiary(beneficiary)
                    .referenceNo(reference)
                    .reasonCode("Invalid Account Number")
                    .status("Unsuccessful")
                    .userId(fromAccount.getUserId())
                    .createdAt(java.time.LocalDateTime.now())
                    .build();
            paymentRepository.save(payment);

            throw new RuntimeException("Account not found");
        }
        if (fromAccount.getBalance().compareTo(amount) < 0){
            Payment payment = Payment.builder()
                    .amount(amount)
                    .accountId(accountUUID)
                    .userId(fromAccount.getUserId())
                    .beneficiaryAccNo(beneficiaryAccNo)
                    .beneficiary(beneficiary)
                    .referenceNo(reference)
                    .reasonCode("Insufficient Balance")
                    .status("Unsuccessful")
                    .createdAt(java.time.LocalDateTime.now())
                    .build();
            paymentRepository.save(payment);
            throw new RuntimeException("Insufficient Balance");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        new Payment();
        Payment payment = Payment.builder()
                .amount(amount)
                .accountId(accountUUID)
                .userId(fromAccount.getUserId())
                .beneficiaryAccNo(beneficiaryAccNo)
                .beneficiary(beneficiary)
                .referenceNo(reference)
                .reasonCode("Payment Successful")
                .status("Success")
                .createdAt(java.time.LocalDateTime.now())
                .build();
        paymentRepository.save(payment);
    }

    @Override
    public List<PaymentDTO> getAllPayments(UUID id) {
   List<Payment> payments = paymentRepository.findAllByUserId(id);
   return payments.
           stream()
           .map(paymentMapper::paymentToPaymentDto)
           .collect(Collectors.toList());
    }
}
