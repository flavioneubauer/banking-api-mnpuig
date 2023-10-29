package com.builderprime.banking.transfer.business;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.builderprime.banking.transfer.model.dto.BankAccountTransferHistoryDto;
import com.builderprime.banking.transfer.model.repository.TransferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountTransferHistory {
    
    private final TransferRepository transferRepository;

    public List<BankAccountTransferHistoryDto> list(String bankAccountId){
        return transferRepository.findTransfersByBankAccount(UUID.fromString(bankAccountId));
    }
}
