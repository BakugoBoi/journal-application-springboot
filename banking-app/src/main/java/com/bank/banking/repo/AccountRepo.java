package com.bank.banking.repo;

import com.bank.banking.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountEntity,Long> {
}
