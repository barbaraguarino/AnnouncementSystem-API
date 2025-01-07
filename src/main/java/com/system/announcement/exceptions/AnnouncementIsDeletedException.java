package com.system.announcement.exceptions;

public class AnnouncementIsDeletedException extends RuntimeException {

    public AnnouncementIsDeletedException() {
        super("Esse anúncio foi deletado. " +
                "Não é permitido qualquer tipo de modificação ou acesso.");
    }
}
