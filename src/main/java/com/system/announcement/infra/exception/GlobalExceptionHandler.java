package com.system.announcement.infra.exception;

import com.system.announcement.exceptions.AuthenticationException;
import com.system.announcement.exceptions.CategoryNotFoundException;
import com.system.announcement.exceptions.CityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<Object> AuthenticationExceptionHandler(AuthenticationException authenticationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authenticationException.getMessage());
    }

    @ExceptionHandler(CityNotFoundException.class)
    private ResponseEntity<Object> CityNotFoundExceptionHandler(CityNotFoundException cityNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cityNotFoundException.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<Object> CategoryNotFoundExceptionHandler(CategoryNotFoundException categoryNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(categoryNotFoundException.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format. Please check your request body.");
    }
}
