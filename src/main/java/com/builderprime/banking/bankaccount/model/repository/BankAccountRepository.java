package com.builderprime.banking.bankaccount.model.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.builderprime.banking.bankaccount.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount,UUID>{    
}
