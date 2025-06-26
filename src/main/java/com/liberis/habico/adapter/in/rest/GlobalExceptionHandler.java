package com.liberis.habico.adapter.in.rest;

import com.liberis.habico.adapter.in.rest.error.ErrorDetail;
import com.liberis.habico.adapter.in.rest.error.ErrorResponse;
import com.liberis.habico.common.exception.DuplicateResourceException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle @Valid on @RequestBody (DVOs/DTOs)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

        List<ErrorDetail> errorDetails = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
                    return new ErrorDetail(fieldName, error.getDefaultMessage());
                })
                .toList();

        ErrorResponse errorResponse = buildErrorResponse(status, request,
                "Validation failed for request body",
                errorDetails
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle @Validated on @RequestParam, @PathVariable, or method level validation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {

        List<ErrorDetail> errorDetails = ex.getConstraintViolations().stream()
                .map(violation -> new ErrorDetail(
                        violation.getPropertyPath().toString(), // Path to the invalid property
                        violation.getMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST, request,
                "Validation failed for request parameters/path variables",
                errorDetails
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // A fallback for any other unhandled exceptions
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> handleDuplicateExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.CONFLICT, request,
                "An unexpected error occurred: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // A fallback for any other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request,
                "An unexpected error occurred: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse buildErrorResponse(HttpStatusCode status, WebRequest request, String message, List<ErrorDetail> errorDetails) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        String method = ((ServletWebRequest) request).getRequest().getMethod();
        return new ErrorResponse(LocalDateTime.now(), status.value(), ((HttpStatus) status).getReasonPhrase(), message, method, path, errorDetails);
    }
}
