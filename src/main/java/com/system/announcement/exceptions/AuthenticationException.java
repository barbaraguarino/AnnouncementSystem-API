package com.system.announcement.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("An error occurred while attempting to login");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
