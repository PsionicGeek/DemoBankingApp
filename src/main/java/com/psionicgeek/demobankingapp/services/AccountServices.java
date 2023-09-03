package com.psionicgeek.demobankingapp.services;

import com.psionicgeek.demobankingapp.models.AccountDTO;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountServices {
    List<AccountDTO> getAccountsList(UUID id);
    BigDecimal getTotalBalance(UUID id);

    @Transactional
    void createAccount(AccountDTO accountDTO);

    @Transactional
    void deposit(BigDecimal amount, UUID accountId);

    @Transactional
    void withdraw(BigDecimal amount, UUID accountId);

    @Transactional
    void transfer(BigDecimal amount, UUID from, UUID to);

}
