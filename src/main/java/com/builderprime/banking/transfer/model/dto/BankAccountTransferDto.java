package com.builderprime.banking.transfer.model.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccountTransferDto {
    
    private String source;
    private String target;
    private BigDecimal amount;
}
