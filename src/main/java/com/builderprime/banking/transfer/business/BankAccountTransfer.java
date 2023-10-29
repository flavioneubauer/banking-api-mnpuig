package com.builderprime.banking.transfer.business;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.builderprime.banking.bankaccount.business.BankAccountBalance;
import com.builderprime.banking.bankaccount.business.BankAccountCheck;
import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.transfer.model.Transfer;
import com.builderprime.banking.transfer.model.dto.BankAccountTransferDto;
import com.builderprime.banking.transfer.model.repository.TransferRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountTransfer {

    private final TransferRepository transferRepository;
    private final BankAccountCheck bankAccountCheck;
    private final BankAccountBalance bankAccountBalance;

    @Transactional
    public void transfer(BankAccountTransferDto bankAccountTransferDto) {
        var source = bankAccountCheck.getBankAccount(bankAccountTransferDto.getSource());
        var target = bankAccountCheck.getBankAccount(bankAccountTransferDto.getTarget());
        checkTransferAmount(bankAccountTransferDto.getAmount());
        checkIfNotSameAccount(source, target);
        bankAccountBalance.withdraw(source, bankAccountTransferDto.getAmount());
        bankAccountBalance.add(target, bankAccountTransferDto.getAmount());
        transferRepository.save(
                Transfer.builder()
                        .amount(bankAccountTransferDto.getAmount())
                        .timestamp(LocalDateTime.now())
                        .source(source)
                        .target(target)
                        .build());
    }

    private void checkTransferAmount(BigDecimal transferAmount) {
        BigDecimal minimumAmount = new BigDecimal(10);
        if (transferAmount == null || transferAmount.compareTo(minimumAmount) < 0) {
            throw new ValidationException("transfer amount must be greater than " + minimumAmount.intValue());
        }
    }

    private void checkIfNotSameAccount(BankAccount source, BankAccount target) {
        if (source.equals(target)) {
            throw new ValidationException("Source and target accounts can't be the same");
        }
    }
}
