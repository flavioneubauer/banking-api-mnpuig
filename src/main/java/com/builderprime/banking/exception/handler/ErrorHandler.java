package com.builderprime.banking.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice @Slf4j public class ErrorHandler {

	@ExceptionHandler(Exception.class) public ResponseEntity<ErrorResponse> catchException(Exception e) {
		log.error("HANDLER EXCEPTION: ", e);
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(getRespostaErro(e, INTERNAL_SERVER_ERROR));
	}

	@ExceptionHandler(NotFoundException.class) public ResponseEntity<ErrorResponse> catchNotFoundException(
			NotFoundException nfe) {
		return ResponseEntity.status(NOT_FOUND).body(getRespostaErro(nfe, NOT_FOUND));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST) @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<ErrorResponse> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		List<ErrorDetail> errors = new ArrayList<>();
		ex.getBindingResult()
				.getAllErrors()
				.forEach(error -> errors.add(ErrorDetail.builder()
						.message(((FieldError) error).getField() + " " + error.getDefaultMessage())
						.build()));
		return ResponseEntity.status(UNPROCESSABLE_ENTITY)
				.body(ErrorResponse.builder()
						.timestamp(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()))
						.statusDescription(UNPROCESSABLE_ENTITY.name())
						.status(UNPROCESSABLE_ENTITY.value())
						.errors(errors)
						.build());
	}

	@ExceptionHandler(ValidationException.class) public ResponseEntity<ErrorResponse> catchValidationException(
			ValidationException ve) {
		return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(getRespostaErro(ve, UNPROCESSABLE_ENTITY));
	}

	@ExceptionHandler(DuplicateKeyException.class) public ResponseEntity<ErrorResponse> catchDuplicateKeyException(
			DuplicateKeyException ve) {
		return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(getRespostaErro(ve, UNPROCESSABLE_ENTITY));
	}

	@ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<ErrorResponse> catchDataIntegrityException(
			DataIntegrityViolationException exception) {
		return ResponseEntity.status(UNPROCESSABLE_ENTITY)
				.body(getErrorResponse(UNPROCESSABLE_ENTITY,
						"Unable to complete the operation due to a foreign key violation."));
	}

	@ExceptionHandler(ConstraintViolationException.class) public ResponseEntity<ErrorResponse> catchConstraintViolationException(
			ConstraintViolationException exception) {
		return ResponseEntity.status(UNPROCESSABLE_ENTITY)
				.body(getErrorResponse(UNPROCESSABLE_ENTITY, exception.getConstraintViolations()
						.stream()
						.map(ConstraintViolation::getMessage)
						.collect(Collectors.joining("\n"))));
	}

	@ExceptionHandler(TransactionSystemException.class) public ResponseEntity<ErrorResponse> catchTransactionException(
			TransactionSystemException exception) {
		return ResponseEntity.status(UNPROCESSABLE_ENTITY)
				.body(getErrorResponse(UNPROCESSABLE_ENTITY,
						exception.getOriginalException() != null && exception.getOriginalException()
								.getCause() != null ?
								exception.getOriginalException().getCause().getMessage() :
								exception.getMessage()));
	}

	private ErrorResponse getRespostaErro(Exception re, HttpStatus status) {
		return ErrorResponse.builder()
				.timestamp(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()))
				.statusDescription(status.name())
				.status(status.value())
				.errors(getErrorDetail(re))
				.build();
	}

	private ErrorResponse getErrorResponse(HttpStatus status, String mensagem) {
		return ErrorResponse.builder()
				.timestamp(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()))
				.statusDescription(status.name())
				.status(status.value())
				.errors(List.of(ErrorDetail.builder().message(mensagem).build()))
				.build();
	}

	private List<ErrorDetail> getErrorDetail(Exception re) {
		return List.of(ErrorDetail.builder().message(re.getMessage()).build());
	}
}
