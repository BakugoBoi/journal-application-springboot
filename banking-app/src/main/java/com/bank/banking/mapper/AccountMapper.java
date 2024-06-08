package com.bank.banking.mapper;

import com.bank.banking.Dto.AccountDto;
import com.bank.banking.entity.AccountEntity;

public class AccountMapper {

    public static AccountEntity mapToAccountEntity(AccountDto accountDto){
        AccountEntity accountEntity = new AccountEntity(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance()
        );
        return accountEntity;
    }

    public static AccountDto mapToAccountDto(AccountEntity accountEntity){
        AccountDto accountDto = new AccountDto(
                accountEntity.getId(),
                accountEntity.getAccHolderName(),
                accountEntity.getBalance()
        );
        return accountDto;
    }
}
