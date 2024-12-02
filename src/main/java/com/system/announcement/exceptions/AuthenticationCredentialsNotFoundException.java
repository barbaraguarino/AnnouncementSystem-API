package com.system.announcement.exceptions;

public class AuthenticationCredentialsNotFoundException extends RuntimeException {
    public AuthenticationCredentialsNotFoundException(String message) {
        super(message);
    }

    public AuthenticationCredentialsNotFoundException() {
        super("Authentication credentials not found.");
    }
}
