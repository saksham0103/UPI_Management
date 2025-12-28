package com.tcs.controller;

import com.tcs.dto.ApiResponseDto;
import com.tcs.dto.FundTransferRequestDto;
import com.tcs.dto.TransactionResponseDto;
import com.tcs.services.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ApiResponseDto transferFunds(@RequestParam Long userId,
                                        @RequestBody FundTransferRequestDto request) {

        transactionService.transferFunds(request, userId);
        return new ApiResponseDto(true, "Transfer successful");
    }

    @GetMapping("/history")
    public List<TransactionResponseDto> getTransactionHistory(
            @RequestParam Long userId) {

        return transactionService.getTransactionHistory(userId);
    }
}
