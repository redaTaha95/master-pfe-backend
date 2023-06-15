package benifit.domain;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(null);
    }

    @ExceptionHandler(BenifitNotFoundException.class)
    public ResponseEntity<Object> handleTaskNotFoundException(BenifitNotFoundException ex, ServerWebExchange exchange) {
        String error = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, error) {
        };
        return handleExceptionInternal(ex, errorResponse, null, HttpStatus.NOT_FOUND, exchange).block();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Object> handleProjectNotFoundException(EmployeeNotFoundException ex, ServerWebExchange exchange) {
        String error = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, error);
        return handleExceptionInternal(ex, errorResponse, null, HttpStatus.NOT_FOUND, exchange).block();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationError(ConstraintViolationException exception) {
        log.warn("ConstraintViolationException thrown", exception);
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            Map<String, String> transformedError = new HashMap<>();

            String fieldName = violation.getPropertyPath().toString();
            transformedError.put("field", fieldName.substring(fieldName.lastIndexOf('.') + 1));
            transformedError.put("error", violation.getMessage());

            errors.add(transformedError);
        }
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, ServerWebExchange exchange) {
        String error = "An internal server error occurred.";
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error);
        return handleExceptionInternal(ex, errorResponse, null, HttpStatus.INTERNAL_SERVER_ERROR, exchange).block();
    }
}
