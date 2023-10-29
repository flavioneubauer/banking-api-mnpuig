package com.builderprime.banking.transfer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.builderprime.banking.bankaccount.model.dto.BankAccountBallanceDto;
import com.builderprime.banking.exception.handler.ErrorResponse;
import com.builderprime.banking.transfer.business.BankAccountTransfer;
import com.builderprime.banking.transfer.business.BankAccountTransferHistory;
import com.builderprime.banking.transfer.model.dto.BankAccountTransferDto;
import com.builderprime.banking.transfer.model.dto.BankAccountTransferHistoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.http.ResponseEntity;

@Tag(name = "BankAccountTransfer", description = "Bank Account transfer operations")
@RequestMapping(value = "/v1/bank-accounts", consumes = { APPLICATION_JSON_VALUE, ALL_VALUE }, produces = {
        APPLICATION_JSON_VALUE })
@RestController
@RequiredArgsConstructor
public class BankAccountTransferController {

    private final BankAccountTransfer bankAccountTransfer;
    private final BankAccountTransferHistory bankAccountTransferHistory;

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

    @Operation(description = "Retrieve bank account transfer history")
    @ApiResponse(responseCode = "200", description = "Bank account history")
    @ApiResponse(responseCode = "422", description = "Validation error",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping(path = "{bankAccountId}/transfers/history")
    public ResponseEntity<List<BankAccountTransferHistoryDto>> getHistory(@PathVariable String bankAccountId){
        return ResponseEntity.ok().body(bankAccountTransferHistory.list(bankAccountId));
    }

}
