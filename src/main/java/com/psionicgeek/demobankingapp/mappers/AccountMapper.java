package com.psionicgeek.demobankingapp.mappers;

import com.psionicgeek.demobankingapp.entities.Account;
import com.psionicgeek.demobankingapp.models.AccountDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    Account accountDtoToAccount(AccountDTO accountDTO);
    AccountDTO accountToAccountDto(Account account);

}
