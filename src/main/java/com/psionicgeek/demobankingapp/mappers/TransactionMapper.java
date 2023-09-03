package com.psionicgeek.demobankingapp.mappers;


import com.psionicgeek.demobankingapp.entities.Transactions;
import com.psionicgeek.demobankingapp.models.TransactionDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
     Transactions transactionDtoToTransaction(TransactionDTO transactionDTO);
     TransactionDTO transactionToTransactionDto(Transactions transaction);

}
