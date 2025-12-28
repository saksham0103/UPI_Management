package com.tcs.repository;

import com.tcs.entity.Account;
import com.tcs.entity.User;
import com.tcs.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByUserAndAccountType(User user, AccountType accountType);
   }

