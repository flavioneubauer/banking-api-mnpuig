package com.builderprime.banking.transfer.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.builderprime.banking.transfer.model.Transfer;
import com.builderprime.banking.transfer.model.dto.BankAccountTransferHistoryDto;

public interface TransferRepository extends CrudRepository<Transfer, UUID> {

    @Query("select new com.builderprime.banking.transfer.model.dto.BankAccountTransferHistoryDto( " 
            + "   t.timestamp as timestamp," 
            + "    t.source.id as sourceAccount,"
            + "    t.target.id as targetAccount,"
            + "    t.source.owner.name as sourceOwner,"
            + "    t.target.owner.name as targetOwner "
            + ") "
            + "from Transfer t "
            + "where t.source.id = :bankAccountId or "
            + "t.target.id = :bankAccountId "
            + "order by t.timestamp desc"
            )
    List<BankAccountTransferHistoryDto> findTransfersByBankAccount(
            @Param("bankAccountId") UUID bankAccountId);
}
