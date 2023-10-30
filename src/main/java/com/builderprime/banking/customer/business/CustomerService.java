package com.builderprime.banking.customer.business;

import com.builderprime.banking.customer.model.Customer;
import com.builderprime.banking.customer.model.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service @RequiredArgsConstructor public class CustomerService {

	private final CustomerRepository customerRepository;

	public Optional<Customer> findById(String uuid) {
		return customerRepository.findById(UUID.fromString(uuid));
	}
}
