package com.psionicgeek.demobankingapp.services;

import com.psionicgeek.demobankingapp.entities.Transactions;
import com.psionicgeek.demobankingapp.mappers.TransactionMapper;
import com.psionicgeek.demobankingapp.models.TransactionDTO;
import com.psionicgeek.demobankingapp.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class TransactionServicesImpl implements TransactionServices{


    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    @Override
    public List<TransactionDTO> getAllTransactions(UUID userId) {
        try {
            List<Transactions> transactions = transactionRepository.findAllByUserID(userId);
            return transactions.stream().map(transactionMapper::transactionToTransactionDto).collect(Collectors.toList());
        }
        catch (Exception e){
            throw new RuntimeException("Something went wrong");
        }


    }
}
