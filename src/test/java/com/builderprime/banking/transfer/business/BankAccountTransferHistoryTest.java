package com.builderprime.banking.transfer.business;

import com.builderprime.banking.transfer.model.dto.BankAccountTransferHistoryDto;
import com.builderprime.banking.transfer.model.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) class BankAccountTransferHistoryTest {

	@Mock TransferRepository transferRepository;

	@InjectMocks BankAccountTransferHistory bankAccountTransferHistory;

	@Test void testSuccessfullBankAccount() {
		var bankAccountId = UUID.randomUUID();
		when(transferRepository.findTransfersByBankAccount(Mockito.any())).thenReturn(
				List.of(new BankAccountTransferHistoryDto(LocalDateTime.now(), UUID.randomUUID(), UUID.randomUUID(),
						"test", "test2")));
		assertEquals(1, bankAccountTransferHistory.list(bankAccountId.toString()).size());
	}

	@Test void testInvalidBankAccount() {
		var bankAccountId = UUID.randomUUID();
		when(transferRepository.findTransfersByBankAccount(Mockito.any())).thenReturn(List.of());
		assertEquals(0, bankAccountTransferHistory.list(bankAccountId.toString()).size());
	}

}