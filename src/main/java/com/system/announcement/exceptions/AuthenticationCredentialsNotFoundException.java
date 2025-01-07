package com.system.announcement.exceptions;

public class AuthenticationCredentialsNotFoundException extends RuntimeException {

    public AuthenticationCredentialsNotFoundException() {
        super("Credencias de autenticação não encontradas ou inválidas.");
    }
}
