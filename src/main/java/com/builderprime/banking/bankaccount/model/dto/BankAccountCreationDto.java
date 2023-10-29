package com.builderprime.banking.bankaccount.model.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccountCreationDto {
    
    private String customerId;
    private String name;
    private BigDecimal depositAmount;
}