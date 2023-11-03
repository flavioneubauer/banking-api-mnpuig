package com.builderprime.banking.bankaccount.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BankAccountCreationDto {

    @NotBlank(message = "Customer can not be empty")
    private String customerId;
    @NotBlank(message = "Name account can not be empty")
    private String name;
    @DecimalMin(value = "0.0", inclusive = false, message = "must be greater than zero")
    private BigDecimal depositAmount;
}