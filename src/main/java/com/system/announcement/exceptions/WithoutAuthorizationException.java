package com.system.announcement.exceptions;

public class WithoutAuthorizationException extends RuntimeException {

    public WithoutAuthorizationException(String message) {
        super(message);
    }

    public WithoutAuthorizationException() {
        super("User does not have authorization.");
    }
}
