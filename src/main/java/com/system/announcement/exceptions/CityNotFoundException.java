package com.system.announcement.exceptions;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(){
        super("City not found");
    }
}
