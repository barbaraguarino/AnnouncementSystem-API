package com.system.announcement.infra.exception;

import com.system.announcement.exceptions.AuthenticationException;
import com.system.announcement.exceptions.CategoryIsEmptyException;
import com.system.announcement.exceptions.DuplicateResourceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<Object> AuthenticationExceptionHandler(AuthenticationException authenticationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authenticationException.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format. Please check your request body.");
    }

    @ExceptionHandler(CategoryIsEmptyException.class)
    private ResponseEntity<Object> CategoryIsEmptyExceptionHandler(CategoryIsEmptyException categoryIsEmptyException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(categoryIsEmptyException.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate resource, please check what was sent.");
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Object> DuplicateResourceExceptionHandler(DuplicateResourceException duplicateResourceException){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(duplicateResourceException.getMessage());
    }


}
