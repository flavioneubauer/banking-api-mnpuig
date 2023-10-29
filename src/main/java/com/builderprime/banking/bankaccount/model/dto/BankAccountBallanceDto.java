package com.builderprime.banking.bankaccount.model.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccountBallanceDto {
    private BigDecimal balance; 
}
