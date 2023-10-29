package com.builderprime.banking.bankaccount.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.builderprime.banking.bankaccount.business.BankAccountBalance;
import com.builderprime.banking.bankaccount.business.BankAccountCreation;
import com.builderprime.banking.bankaccount.model.dto.BankAccountBallanceDto;
import com.builderprime.banking.bankaccount.model.dto.BankAccountCreationDto;
import com.builderprime.banking.exception.handler.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "BankAccount", description = "Bank Account basic operations")
@RequestMapping(value = "/v1/bank-accounts",
        consumes = {APPLICATION_JSON_VALUE, ALL_VALUE},
        produces = {APPLICATION_JSON_VALUE})
@RestController
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountCreation bankAccountCreation;
    private final BankAccountBalance bankAccountBallance;
    
    @Operation(description = "Create a bank account for a customer")
    @ApiResponse(responseCode = "201", description = "Bank acccount succesfully created")
    @ApiResponse(responseCode = "422", description = "Validation error",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(consumes = {APPLICATION_JSON_VALUE, ALL_VALUE},
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> create(@RequestBody BankAccountCreationDto bankAccountCreationDto) {
        bankAccountCreation.create(bankAccountCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(description = "Retrieve bank account balance")
    @ApiResponse(responseCode = "200", description = "Bank account data")
    @ApiResponse(responseCode = "422", description = "Validation error",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping(path = "{bankAccountId}")
    public ResponseEntity<BankAccountBallanceDto> getBalance(@PathVariable String bankAccountId){
        return ResponseEntity.ok().body(bankAccountBallance.getBallance(bankAccountId));
    }
}
