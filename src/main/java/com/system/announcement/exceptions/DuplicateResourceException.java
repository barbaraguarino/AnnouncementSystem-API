package com.system.announcement.exceptions;

public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException() {
        super("Duplicate resource, please check what was sent.");
    }

    public DuplicateResourceException(String message) {
        super(message);
    }
}
