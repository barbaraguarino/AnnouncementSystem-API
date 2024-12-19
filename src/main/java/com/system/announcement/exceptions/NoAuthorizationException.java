package com.system.announcement.exceptions;

public class NoAuthorizationException extends RuntimeException {
    public NoAuthorizationException() {
        super("No authorization required");
    }

    public NoAuthorizationException(String message) {
        super(message);
    }
}
