package com.builderprime.banking.transfer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.builderprime.banking.exception.handler.ErrorResponse;
import com.builderprime.banking.transfer.business.BankAccountTransfer;
import com.builderprime.banking.transfer.model.dto.BankAccountTransferDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;

@Tag(name = "BankAccountTransfer", description = "Bank Account transfer operations")
@RequestMapping(value = "/v1/bank-account", consumes = { APPLICATION_JSON_VALUE, ALL_VALUE }, produces = {
        APPLICATION_JSON_VALUE })
@RestController
@RequiredArgsConstructor
public class BankAccountTransferController {

    private final BankAccountTransfer bankAccountTransfer;

    @Operation(description = "Create a transfer between account")
    @ApiResponse(responseCode = "201", description = "Transfer created succesfully created")
    @ApiResponse(responseCode = "422", description = "Validation error",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PostMapping(path = "transfer", consumes = {APPLICATION_JSON_VALUE, ALL_VALUE},
            produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> transfer(@RequestBody BankAccountTransferDto bankAccountTransferDto){
        bankAccountTransfer.transfer(bankAccountTransferDto);
        return ResponseEntity.status(CREATED).build();
    }

}
