package com.builderprime.banking.bankaccount.business;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.bankaccount.model.repository.BankAccountRepository;
import com.builderprime.banking.exception.NotFoundException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountCheck {

    private final BankAccountRepository bankAccountRepository;
    
    public BankAccount getBankAccount(@NonNull String bankAccountId){
        var bankAccount = bankAccountRepository.findById(UUID.fromString(bankAccountId));
        if(bankAccount.isEmpty()){
            throw new NotFoundException("Bank account " + bankAccountId + " not found.");
        }
        return bankAccount.get(); 
    }
}
