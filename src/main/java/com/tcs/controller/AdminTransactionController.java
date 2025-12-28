package com.tcs.controller;

import com.tcs.entity.Transaction;
import com.tcs.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/transaction")
public class AdminTransactionController {
		
    private final TransactionRepository transactionRepository;

    public AdminTransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // View all transactions
    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
