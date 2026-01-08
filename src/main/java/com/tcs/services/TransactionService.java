package com.tcs.services;

import com.tcs.dto.FundTransferRequestDto;
import com.tcs.dto.TransactionResponseDto;

import java.util.List;

public interface TransactionService {
    void transferFunds(FundTransferRequestDto request, Long userId);
    List<TransactionResponseDto> getTransactionHistory(Long userId);
}
