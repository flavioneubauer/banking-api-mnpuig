package com.builderprime.banking.bankaccount.business;

import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.bankaccount.model.dto.BankAccountCreationDto;
import com.builderprime.banking.bankaccount.model.repository.BankAccountRepository;
import com.builderprime.banking.customer.business.CustomerService;
import com.builderprime.banking.customer.model.Customer;
import com.builderprime.banking.exception.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) class BankAccountCreationTest {

	@Mock BankAccountRepository bankAccountRepository;

	@Mock CustomerService customerService;

	@InjectMocks BankAccountCreation bankAccountCreation;

	private static Validator validator;

	@BeforeAll public static void setupValidatorInstance() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	private String customerId;
	private String customerName = "John doe";
	private String accountName = "my account";
	private Customer customer;
	private BankAccountCreationDto bankAccount;

	@BeforeEach void onBeforeEach() {
		customerId = UUID.randomUUID().toString();
		customer = Customer.builder().id(UUID.fromString(customerId)).name(customerName).build();
		bankAccount = BankAccountCreationDto.builder()
				.name(accountName)
				.customerId(customerId)
				.depositAmount(new BigDecimal(2))
				.build();
	}

	@Test void testshouldThrowErrorWithoutCustomer() {
		when(customerService.findById(customerId)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> bankAccountCreation.create(bankAccount));
	}

	@Test void testShouldThrowErrorZeroDepositAmount() {
		Set<ConstraintViolation<BankAccount>> violations = validator.validate(
				BankAccount.builder().balance(new BigDecimal(0)).name(bankAccount.getName()).owner(customer).build());
		assertThat(violations).hasSize(1);
	}

	@Test void testShouldThrowErrorNegativeDepositAmount() {
		Set<ConstraintViolation<BankAccount>> violations = validator.validate(
				BankAccount.builder().balance(new BigDecimal(-100)).name(bankAccount.getName()).owner(customer).build());
		assertThat(violations).hasSize(1);
	}

	@Test void testShouldNotThrowErrorPositiveAmount() {
		Set<ConstraintViolation<BankAccount>> violations = validator.validate(
				BankAccount.builder().balance(new BigDecimal(0.1)).name(bankAccount.getName()).owner(customer).build());
		assertThat(violations).hasSize(0);
	}

	@Test void testSuccessfulBankAccountCreation() {
		lenient().when(customerService.findById(customerId)).thenReturn(Optional.of(customer));
		assertDoesNotThrow(() -> bankAccountCreation.create(bankAccount));
	}

}
