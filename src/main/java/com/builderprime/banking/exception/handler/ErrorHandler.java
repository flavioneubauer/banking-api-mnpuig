package com.builderprime.banking.exception.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ValidationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> catchException(Exception e) {
        log.error("HANDLER EXCEPTION: ", e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(getRespostaErro(e, INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> catchNotFoundException(NotFoundException nfe) {
        return ResponseEntity.status(NOT_FOUND).body(getRespostaErro(nfe, NOT_FOUND));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> catchValidationException(ValidationException ve) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(getRespostaErro(ve, UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> catchDuplicateKeyException(DuplicateKeyException ve) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(getRespostaErro(ve, UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> catchConstraintViolationException(DataIntegrityViolationException exception) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(getErrorResponse(UNPROCESSABLE_ENTITY,
                "Unable to complete the operation due to a foreign key violation."));
    }

    private ErrorResponse getRespostaErro(Exception re,
            HttpStatus status) {
        return ErrorResponse.builder()
                .timestamp(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()))
                .statusDescription(status.name())
                .status(status.value())
                .errors(getErrorDetail(re))
                .build();
    }

    private ErrorResponse getErrorResponse(
            HttpStatus status, String mensagem) {
        return ErrorResponse.builder()
                .timestamp(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()))
                .statusDescription(status.name())
                .status(status.value())
                .errors(List.of(ErrorDetail.builder()
                        .message(mensagem)
                        .build()))
                .build();
    }

    private List<ErrorDetail> getErrorDetail(Exception re) {
        return List.of(ErrorDetail.builder()
                .message(re.getMessage())
                .build());
    }
}
