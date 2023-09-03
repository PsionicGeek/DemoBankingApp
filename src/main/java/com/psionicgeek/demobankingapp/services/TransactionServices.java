package com.psionicgeek.demobankingapp.services;

import com.psionicgeek.demobankingapp.entities.Transactions;
import com.psionicgeek.demobankingapp.models.TransactionDTO;

import java.util.List;
import java.util.UUID;

public interface TransactionServices {

    List<TransactionDTO> getAllTransactions(UUID userId);
}
