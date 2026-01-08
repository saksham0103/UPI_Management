package com.tcs.controller;

import com.tcs.dto.ApiResponseDto;
import com.tcs.dto.FundTransferRequestDto;
import com.tcs.dto.TransactionResponseDto;
import com.tcs.entity.User;
import com.tcs.repository.UserRepository;
import com.tcs.services.TransactionService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;
    
    

    public TransactionController(TransactionService transactionService, UserRepository userRepository) {
		super();
		this.transactionService = transactionService;
		this.userRepository = userRepository;
	}

	@PostMapping("/transfer")
    public ApiResponseDto transferFunds(
            Authentication auth,
            @RequestBody FundTransferRequestDto request) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        transactionService.transferFunds(request, user.getId());
        return new ApiResponseDto(true, "Transfer successful");
    }

    @GetMapping("/history")
    public List<TransactionResponseDto> getTransactionHistory(Authentication auth) {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return transactionService.getTransactionHistory(user.getId());
    }
}
