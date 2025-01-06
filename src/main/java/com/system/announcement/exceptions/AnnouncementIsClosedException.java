package com.system.announcement.exceptions;

public class AnnouncementIsClosedException extends RuntimeException {

    public AnnouncementIsClosedException(String message) {
        super(message);
    }

    public AnnouncementIsClosedException() {
        super("Esse anúncio está fechado. " +
                "Não é permitido qualquer tipo de modificação.");
    }
}
