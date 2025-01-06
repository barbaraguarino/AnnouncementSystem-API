package com.system.announcement.exceptions;

public class WithoutAuthorizationException extends RuntimeException {

    public WithoutAuthorizationException() {
        super("Usuário não possui autorização ou permissão para realizar requisição.");
    }
}
