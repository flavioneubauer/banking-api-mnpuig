package com.builderprime.banking.bankaccount.business;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.bankaccount.model.dto.BankAccountBallanceDto;
import com.builderprime.banking.bankaccount.model.repository.BankAccountRepository;
import com.builderprime.banking.exception.NotFoundException;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountBalance {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountBallanceDto getBallance(String bankAccountId){
        var bankAccountOptional = bankAccountRepository.findById(UUID.fromString(bankAccountId));
        if(bankAccountOptional.isEmpty()){
            throw new NotFoundException("Bank account not found");
        }
        var balance = bankAccountOptional.get().getBalance();
        return BankAccountBallanceDto.builder().balance(balance).build();
    }

    public void withdraw(BankAccount bankAccount, BigDecimal amount){
        var existingBalance = bankAccount.getBalance();
        checkExistingAmount(existingBalance, amount);
        var newAmount = existingBalance.subtract(amount);
        bankAccount.setBalance(newAmount);
    }

    public void add(BankAccount bankAccount, BigDecimal amount){
        var existingBalance = bankAccount.getBalance();
        var newAmount = existingBalance.add(amount);
        bankAccount.setBalance(newAmount);
    }

    private void checkExistingAmount(BigDecimal existingAmount, BigDecimal withdrawAmount){
        if(existingAmount.compareTo(withdrawAmount) < 0){
            throw new ValidationException("Insufficient credit");
        }
    }

}