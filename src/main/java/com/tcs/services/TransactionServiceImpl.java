package com.tcs.services;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.dto.FundTransferRequestDto;
import com.tcs.dto.TransactionResponseDto;
import com.tcs.entity.Account;
import com.tcs.entity.Transaction;
import com.tcs.entity.User;
import com.tcs.enums.AccountStatus;
import com.tcs.enums.TransactionStatus;
import com.tcs.enums.TransactionType;
import com.tcs.repository.AccountRepository;
import com.tcs.repository.TransactionRepository;
import com.tcs.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final OtpService otpService;
    
    

    public TransactionServiceImpl(UserRepository userRepository, AccountRepository accountRepository,
			TransactionRepository transactionRepository, OtpService otpService) {
		super();
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
		this.otpService = otpService;
	}

	@Override
    @Transactional
    public void transferFunds(FundTransferRequestDto request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account sender = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User has no account"));

        if (sender.getStatus() != AccountStatus.ACTIVE)
            throw new RuntimeException("Sender account inactive");

        otpService.validateOtp(userId, request.getOtp());

        Account receiver = accountRepository
                .findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (receiver.getStatus() != AccountStatus.ACTIVE)
            throw new RuntimeException("Receiver account inactive");

        BigDecimal amount = BigDecimal.valueOf(request.getAmount());

        if (sender.getBalance().compareTo(amount) < 0)
            throw new RuntimeException("Insufficient balance");

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setFromAccount(sender.getAccountNumber());
        tx.setToAccount(receiver.getAccountNumber());
        tx.setAmount(amount);
        tx.setTransactionType(TransactionType.DEBIT);
        tx.setStatus(TransactionStatus.SUCCESS.name());
        tx.setTransactionTime(LocalDateTime.now());

        transactionRepository.save(tx);
    }

    @Override
    public List<TransactionResponseDto> getTransactionHistory(Long userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(tx -> {
                    TransactionResponseDto dto = new TransactionResponseDto();
                    dto.setFromAccount(tx.getFromAccount());
                    dto.setToAccount(tx.getToAccount());
                    dto.setAmount(tx.getAmount().doubleValue());
                    dto.setStatus(tx.getStatus());
                    dto.setTransactionTime(tx.getTransactionTime());
                    return dto;
                })
                .toList();
    }
}
