package com.psionicgeek.demobankingapp.services;

import com.psionicgeek.demobankingapp.entities.Account;
import com.psionicgeek.demobankingapp.entities.Transactions;
import com.psionicgeek.demobankingapp.mappers.AccountMapper;
import com.psionicgeek.demobankingapp.models.AccountDTO;
import com.psionicgeek.demobankingapp.repositories.AccountRepository;
import com.psionicgeek.demobankingapp.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
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
public class AccountServicesImpl implements AccountServices{

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransactionRepository transactionRepository;
    @Override
    public List<AccountDTO> getAccountsList(UUID id) {
        try {
            return accountRepository.findAllByUserId(id)
                    .stream()
                    .map(accountMapper::accountToAccountDto)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public BigDecimal getTotalBalance(UUID id) {
        try {
            return accountRepository.getTotalBalance(id);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    @Transactional
    public void createAccount(AccountDTO accountDTO) {
        Account account = accountMapper.accountDtoToAccount(accountDTO);
        accountRepository.save(account);

    }

    @Override
    @Transactional
    public void deposit(BigDecimal amount, UUID accountId) {

        Account account = accountRepository.findByAccountId(accountId);
        BigDecimal balance = account.getBalance();
        BigDecimal newBalance = balance.add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
        Transactions transactions = Transactions.builder()
                .accountId(accountId)
                .amount(amount)
                .status("Success")
                .reasonCode("Deposit Successful")
                .transactionType("Deposit")
                .source("Online")
                .userID(account.getUserId())
                .createdAt(java.time.LocalDateTime.now())
                .build();
        transactionRepository.save(transactions);


    }

    @Override
    public void withdraw(BigDecimal amount, UUID accountId) {
        Account account = accountRepository.findByAccountId(accountId);
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(amount) < 0){
            Transactions transactions = Transactions.builder()
                    .accountId(accountId)
                    .amount(amount)
                    .status("Unsuccessful")
                    .reasonCode("Insufficient Balance")
                    .transactionType("Withdraw")
                    .source("Online")
                    .userID(account.getUserId())
                    .createdAt(java.time.LocalDateTime.now())
                    .build();
            transactionRepository.save(transactions);
            throw new RuntimeException("Insufficient Balance");
        }
        BigDecimal newBalance = balance.subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
        Transactions transactions = Transactions.builder()
                .accountId(accountId)
                .amount(amount)
                .status("Success")
                .reasonCode("Withdraw Successful")
                .transactionType("Withdraw")
                .source("Online")
                .userID(account.getUserId())
                .createdAt(java.time.LocalDateTime.now())
                .build();
        transactionRepository.save(transactions);

    }

    @Override
    public void transfer(BigDecimal amount, UUID from, UUID to) {
        Account fromAccount = accountRepository.findByAccountId(from);
        Account toAccount = accountRepository.findByAccountId(to);
        BigDecimal fromBalance = fromAccount.getBalance();
        BigDecimal toBalance = toAccount.getBalance();
        if (fromBalance.compareTo(amount) < 0){
            Transactions transactions = Transactions.builder()
                    .accountId(from)
                    .amount(amount)
                    .status("Unsuccessful")
                    .reasonCode("Insufficient Balance")
                    .transactionType("Transfer")
                    .source("Online")
                    .userID(fromAccount.getUserId())
                    .createdAt(java.time.LocalDateTime.now())
                    .build();
            transactionRepository.save(transactions);
            throw new RuntimeException("Insufficient Balance");
        }
        BigDecimal newFromBalance = fromBalance.subtract(amount);
        BigDecimal newToBalance = toBalance.add(amount);
        fromAccount.setBalance(newFromBalance);
        toAccount.setBalance(newToBalance);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        Transactions transactions = Transactions.builder()
                .accountId(from)
                .amount(amount)
                .status("Success")
                .reasonCode("Transfer Successful")
                .transactionType("Transfer")
                .source("Online")
                .userID(fromAccount.getUserId())
                .createdAt(java.time.LocalDateTime.now())
                .build();
        transactionRepository.save(transactions);

    }
}
