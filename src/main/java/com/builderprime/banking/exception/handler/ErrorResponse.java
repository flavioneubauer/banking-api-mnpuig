package com.builderprime.banking.exception.handler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String timestamp;
    private Integer status;
    private String statusDescription;
    private List<ErrorDetail> errors;
}
