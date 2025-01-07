package com.system.announcement.exceptions;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(){
        super("Cidade não encontrada. Por favor, verifique os dados e tente novamente.");
    }
}
