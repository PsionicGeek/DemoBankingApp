package com.psionicgeek.demobankingapp.repositories;

import com.psionicgeek.demobankingapp.entities.Account;
import com.psionicgeek.demobankingapp.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findAllByUserId(UUID id);

    @Query(value = "SELECT sum(balance) FROM account WHERE user_id = :id", nativeQuery = true)
    BigDecimal getTotalBalance(UUID id);


    Account findByAccountId(UUID accountId);
    Account findByAccountNumberAndAccountName(String accountNumber, String accountName);
}
