package com.builderprime.banking.bankaccount.business;

import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.bankaccount.model.repository.BankAccountRepository;
import com.builderprime.banking.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) class BankAccountCheckTest {

	@Mock BankAccountRepository bankAccountRepository;

	@InjectMocks BankAccountCheck bankAccountCheck;

	@Test void testNullBankAccount() {
		assertThrows(NullPointerException.class, () -> bankAccountCheck.getBankAccount(null));
	}

	@Test void testInvalidBankAccount() {
		var bankAccountId = UUID.randomUUID().toString();
		when(bankAccountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> bankAccountCheck.getBankAccount(bankAccountId));
	}

	@Test void testValidBankAccount() {
		var bankAccount = BankAccount.builder().id(UUID.randomUUID()).build();
		when(bankAccountRepository.findById(Mockito.any())).thenReturn(Optional.of(bankAccount));
		assertEquals(bankAccount, bankAccountCheck.getBankAccount(bankAccount.getId().toString()));
	}
}