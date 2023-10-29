package com.builderprime.banking.customer.business;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.builderprime.banking.customer.model.Customer;
import com.builderprime.banking.customer.model.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepository;

    public Optional<Customer> findById(String uuid){
        return customerRepository.findById(UUID.fromString(uuid));
    }
}
