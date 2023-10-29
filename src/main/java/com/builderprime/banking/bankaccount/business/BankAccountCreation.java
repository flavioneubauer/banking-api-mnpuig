package com.builderprime.banking.bankaccount.business;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.bankaccount.model.dto.BankAccountCreationDto;
import com.builderprime.banking.bankaccount.model.repository.BankAccountRepository;
import com.builderprime.banking.customer.business.CustomerService;
import com.builderprime.banking.customer.model.Customer;
import com.builderprime.banking.exception.NotFoundException;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountCreation {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerService customerService;

    private void checkDepositAmount(BigDecimal depositAmount) {
        BigDecimal minimumAmount = new BigDecimal(1);
        if (depositAmount == null || depositAmount.compareTo(minimumAmount) < 0) {
            throw new ValidationException("deposit amount must be greater than 1");
        }
    }

    private Customer getCustomer(String customerId) {
        var optionalCustomer = customerService.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        return optionalCustomer.get();
    }

    @Transactional
    public void create(BankAccountCreationDto bankAccountCreationDto) {
        checkDepositAmount(bankAccountCreationDto.getDepositAmount());
        var customer = getCustomer(bankAccountCreationDto.getCustomerId());
        bankAccountRepository.save(BankAccount.builder().balance(bankAccountCreationDto.getDepositAmount())
                .name(bankAccountCreationDto.getName())
                .owner(customer).build());
    }
}
