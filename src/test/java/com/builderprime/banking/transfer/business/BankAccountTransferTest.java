package com.builderprime.banking.transfer.business;

import com.builderprime.banking.bankaccount.business.BankAccountBalance;
import com.builderprime.banking.bankaccount.business.BankAccountCheck;
import com.builderprime.banking.bankaccount.model.BankAccount;
import com.builderprime.banking.exception.NotFoundException;
import com.builderprime.banking.transfer.model.dto.BankAccountTransferDto;
import com.builderprime.banking.transfer.model.repository.TransferRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) class BankAccountTransferTest {

	@Mock TransferRepository transferRepository;

	@Mock BankAccountCheck bankAccountCheck;

	@Mock BankAccountBalance bankAccountBalance;

	@InjectMocks BankAccountTransfer bankAccountTransfer;

	private UUID source;
	private UUID target;
	private BankAccountTransferDto bankAccountTransferDto;

	@BeforeEach void onBeforeEach() {
		bankAccountTransfer = new BankAccountTransfer(transferRepository, bankAccountCheck, bankAccountBalance);
		source = UUID.randomUUID();
		target = UUID.randomUUID();
		bankAccountTransferDto = BankAccountTransferDto.builder()
				.source(source.toString())
				.target(target.toString())
				.amount(new BigDecimal(15))
				.build();
	}

	@Test void testAccountNotValid() {
		when(bankAccountCheck.getBankAccount(source.toString())).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> bankAccountTransfer.transfer(bankAccountTransferDto));
	}

	@Test void testInvalidAmount() {
		when(bankAccountCheck.getBankAccount(source.toString())).thenReturn(
				BankAccount.builder().id(source).balance(new BigDecimal(100)).name("test").build());
		when(bankAccountCheck.getBankAccount(target.toString())).thenReturn(
				BankAccount.builder().id(target).balance(new BigDecimal(100)).name("test").build());
		bankAccountTransferDto.setAmount(new BigDecimal(2));
		assertThrows(ValidationException.class, () -> bankAccountTransfer.transfer(bankAccountTransferDto));
	}

	@Test void testEqualAccounts() {
		var bankAccount = BankAccount.builder().id(source).balance(new BigDecimal(100)).name("test").build();
		when(bankAccountCheck.getBankAccount(source.toString())).thenReturn(bankAccount);
		bankAccountTransferDto.setSource(source.toString());
		bankAccountTransferDto.setTarget(source.toString());
		assertThrows(ValidationException.class, () -> bankAccountTransfer.transfer(bankAccountTransferDto));
	}

	@Test void testPersistSuccessfully() {
		when(bankAccountCheck.getBankAccount(source.toString())).thenReturn(
				BankAccount.builder().id(source).balance(new BigDecimal(100)).name("test").build());
		when(bankAccountCheck.getBankAccount(target.toString())).thenReturn(
				BankAccount.builder().id(target).balance(new BigDecimal(100)).name("test").build());
		assertDoesNotThrow(() -> bankAccountTransfer.transfer(bankAccountTransferDto));
	}

}
