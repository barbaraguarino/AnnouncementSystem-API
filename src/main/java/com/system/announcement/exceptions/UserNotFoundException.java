package com.system.announcement.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuário não encontrado. Por favor, verifique os dados e tente novamente.");
    }
}
