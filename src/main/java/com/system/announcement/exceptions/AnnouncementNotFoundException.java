package com.system.announcement.exceptions;

public class AnnouncementNotFoundException extends RuntimeException{

    public AnnouncementNotFoundException(){
        super("Anúncio não encontrado.");
    }

}
