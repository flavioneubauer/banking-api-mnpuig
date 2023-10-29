package com.builderprime.banking.transfer.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountTransferHistoryDto {

    private LocalDateTime timestamp;
    private String sourceAccount;
    private String targetAccount;
    private String sourceOwner;
    private String targetOwner;

    public BankAccountTransferHistoryDto(LocalDateTime timestamp, UUID sourceAccount, UUID targetAccount,
            String sourceOwner, String targetOwner) {
        this.timestamp = timestamp;
        this.sourceAccount = sourceAccount.toString();
        this.targetAccount = targetAccount.toString();
        this.sourceOwner = sourceOwner;
        this.targetOwner = targetOwner;
    }
}
