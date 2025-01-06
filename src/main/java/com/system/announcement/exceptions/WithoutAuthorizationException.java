package com.system.announcement.exceptions;

public class WithoutAuthorizationException extends RuntimeException {

    public WithoutAuthorizationException(String message) {
        super(message);
    }

    public WithoutAuthorizationException() {
        super("Usuário não possui autorização para realizar requisição.");
    }
}
