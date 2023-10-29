package com.builderprime.banking.transfer.model.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.builderprime.banking.transfer.model.Transfer;

public interface TransferRepository extends CrudRepository<Transfer,UUID> {
    
}
