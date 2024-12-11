package com.system.announcement.exceptions;

public class AdClosedException extends RuntimeException {

    public AdClosedException(String message) {
        super(message);
    }

    public AdClosedException() {
        super("The ad is closed. You are not authorized to make the request!");
    }
}
