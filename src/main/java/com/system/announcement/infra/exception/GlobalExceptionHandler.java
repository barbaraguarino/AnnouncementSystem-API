package com.system.announcement.infra.exception;

import com.system.announcement.exceptions.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<Object> AuthenticationExceptionHandler(AuthenticationException authenticationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while attempting to login");
    }
}
