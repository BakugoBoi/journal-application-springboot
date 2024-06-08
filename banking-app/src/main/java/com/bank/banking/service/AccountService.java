package com.bank.banking.service;

import com.bank.banking.Dto.AccountDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto addDeposit(Long id,Double deposit);

    void deleteAccountById(Long id);

    List<AccountDto> getAllAccounts();
}
