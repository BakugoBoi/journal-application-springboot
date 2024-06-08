package com.bank.banking.service.Impl;

import com.bank.banking.Dto.AccountDto;
import com.bank.banking.entity.AccountEntity;
import com.bank.banking.mapper.AccountMapper;
import com.bank.banking.repo.AccountRepo;
import com.bank.banking.service.AccountService;
import com.fasterxml.jackson.databind.node.DoubleNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepo accountRepo;

    public AccountServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        AccountEntity accountEntity = AccountMapper.mapToAccountEntity(accountDto);
        AccountEntity saved = accountRepo.save(accountEntity);
        return AccountMapper.mapToAccountDto(saved);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        AccountEntity accountEntity = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(accountEntity);
    }

    @Override
    public AccountDto addDeposit(Long id,Double deposit) {
        AccountEntity accountEntity = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        Double total = accountEntity.getBalance() + deposit;
        accountEntity.setBalance(total);
        AccountEntity saved = accountRepo.save(accountEntity);
        return AccountMapper.mapToAccountDto(saved);
    }

    @Override
    public void deleteAccountById(Long id) {
        AccountEntity accountEntity = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        accountRepo.deleteById(id);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<AccountEntity> all = accountRepo.findAll();
        List<AccountDto> collect = all.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        return collect;
    }

}
