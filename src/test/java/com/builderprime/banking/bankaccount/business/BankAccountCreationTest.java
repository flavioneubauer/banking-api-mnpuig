package com.builderprime.banking.bankaccount.business;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.bankaccount.model.dto.BankAccountCreationDto;
import com.builderprime.banking.bankaccount.model.repository.BankAccountRepository;
import com.builderprime.banking.customer.business.CustomerService;
import com.builderprime.banking.customer.model.Customer;
import com.builderprime.banking.customer.model.repository.CustomerRepository;
import com.builderprime.banking.exception.NotFoundException;

import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BankAccountCreationTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    BankAccountRepository bankAccountRepository;

    @Autowired
    BankAccountCreation bankAccountCreation;

    private String customerId;
    private String customerName = "John doe";
    private String accountName = "my account";
    private Customer customer;
    private BankAccountCreationDto bankAccount;

    @BeforeEach
    void onBeforeEach() {
        customerId = UUID.randomUUID().toString();
        customer = Customer.builder().id(UUID.fromString(customerId)).name(customerName).build();
        bankAccount = BankAccountCreationDto.builder().name(accountName).customerId(customerId)
                .depositAmount(new BigDecimal(2)).build();
    }

    @Test
    void testshouldThrowErrorWithoutCustomer(){
        when(customerService.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> 
        bankAccountCreation.create(bankAccount));
    }

    @Test
    void testshouldThrowErrorDepositAmountBelowMinimum(){
        when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        bankAccount.setDepositAmount(new BigDecimal(0));
        assertThrows(ValidationException.class, () -> 
        bankAccountCreation.create(bankAccount));
    }

    @Test
    void testSuccesfullBankAccountCreation(){
        when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
        assertThrows(NotFoundException.class, () -> 
        bankAccountCreation.create(bankAccount));
    }

}
