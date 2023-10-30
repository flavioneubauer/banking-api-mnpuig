package com.builderprime.banking.bankaccount.business;

import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.bankaccount.model.repository.BankAccountRepository;
import com.builderprime.banking.exception.NotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) class BankAccountBalanceTest {

	@Mock BankAccountRepository bankAccountRepository;

	@InjectMocks BankAccountBalance bankAccountBalance;

	@Test void testInvalidBankAccount() {
		when(bankAccountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> bankAccountBalance.getBallance(UUID.randomUUID().toString()));
	}

	@Test void testValidBankAccount() {
		when(bankAccountRepository.findById(Mockito.any())).thenReturn(
				Optional.of(BankAccount.builder().balance(new BigDecimal(100)).build()));
		assertEquals(new BigDecimal(100), bankAccountBalance.getBallance(UUID.randomUUID().toString()).getBalance());
	}

	@Test void testWithdrawSuccesfully() {
		var bankAccount = BankAccount.builder().balance(new BigDecimal(100)).build();
		bankAccountBalance.withdraw(bankAccount, new BigDecimal(2));
		assertEquals(new BigDecimal(98), bankAccount.getBalance());
	}

	@Test void testInvalidWithdraw() {
		var bankAccount = BankAccount.builder().balance(new BigDecimal(1)).build();
		assertThrows(ValidationException.class, () -> bankAccountBalance.withdraw(bankAccount, new BigDecimal(2)));
	}

	@Test void testAddSuccesfully() {
		var bankAccount = BankAccount.builder().balance(new BigDecimal(10)).build();
		bankAccountBalance.add(bankAccount, new BigDecimal(2));
		assertEquals(new BigDecimal(12), bankAccount.getBalance());
	}
}