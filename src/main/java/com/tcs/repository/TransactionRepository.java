package com.tcs.repository;

import com.tcs.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccount(String accountNumber);

    List<Transaction> findByToAccount(String accountNumber);

    List<Transaction> findByUserId(Long userId);
   

}
