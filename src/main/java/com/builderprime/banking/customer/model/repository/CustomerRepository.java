package com.builderprime.banking.customer.model.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.builderprime.banking.customer.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer,UUID>{
}
