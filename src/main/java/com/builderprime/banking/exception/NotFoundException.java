package com.builderprime.banking.exception;

public class NotFoundException extends RuntimeException {
   public NotFoundException(String message) {
      super(message);
   }
}