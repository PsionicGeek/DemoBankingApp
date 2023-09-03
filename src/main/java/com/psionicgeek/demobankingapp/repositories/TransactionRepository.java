package com.psionicgeek.demobankingapp.repositories;


import com.psionicgeek.demobankingapp.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, UUID> {
    List<Transactions> findAllByUserID(UUID userId);
}
