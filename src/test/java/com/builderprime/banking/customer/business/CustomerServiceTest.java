package com.builderprime.banking.customer.business;

import com.builderprime.banking.customer.model.Customer;
import com.builderprime.banking.customer.model.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) class CustomerServiceTest {

	@Mock CustomerRepository customerRepository;

	@InjectMocks CustomerService customerService;

	@Test void testInvalidCustomer() {
		when(customerRepository.findById(any())).thenReturn(Optional.empty());
		assertTrue(customerService.findById(UUID.randomUUID().toString()).isEmpty());
	}

	@Test void testValidCustomer() {
		when(customerRepository.findById(any())).thenReturn(
				Optional.of(Customer.builder().id(UUID.randomUUID()).build()));
		assertTrue(customerService.findById(UUID.randomUUID().toString()).isPresent());
	}

}