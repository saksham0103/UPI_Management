package com.tcs.services;

import com.tcs.dto.FundTransferRequestDto;
import com.tcs.dto.TransactionResponseDto;
import com.tcs.entity.Account;
import com.tcs.entity.Transaction;
import com.tcs.enums.AccountStatus;
import com.tcs.enums.TransactionStatus;
import com.tcs.enums.TransactionType;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.TransactionRepository;
import com.tcs.services.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository,
                                  TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public void transferFunds(FundTransferRequestDto request, Long userId) {

        Account sender = accountRepository.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account receiver = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getStatus() != AccountStatus.ACTIVE ||
            receiver.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Account inactive");
        }

        BigDecimal amount = BigDecimal.valueOf(request.getAmount());

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction tx = new Transaction();
        tx.setFromAccount(request.getFromAccount());
        tx.setToAccount(request.getToAccount());
        tx.setAmount(amount);
        tx.setTransactionType(TransactionType.DEBIT);
        tx.setStatus(TransactionStatus.SUCCESS.name());
        tx.setTransactionTime(LocalDateTime.now());

        transactionRepository.save(tx);
    }

    @Override
    public List<TransactionResponseDto> getTransactionHistory(Long userId) {

        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        List<TransactionResponseDto> response = new ArrayList<>();

        for (Transaction tx : transactions) {
            TransactionResponseDto dto = new TransactionResponseDto();
            dto.setFromAccount(tx.getFromAccount());
            dto.setToAccount(tx.getToAccount());
            dto.setAmount(tx.getAmount().doubleValue());
            dto.setStatus(tx.getStatus());
            dto.setTransactionTime(tx.getTransactionTime());
            response.add(dto);
        }

        return response;
    }
}
