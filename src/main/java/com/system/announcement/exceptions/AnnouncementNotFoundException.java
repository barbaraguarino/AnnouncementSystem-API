package com.system.announcement.exceptions;

public class AnnouncementNotFoundException extends RuntimeException{

    public AnnouncementNotFoundException(){
        super("Announcement not found");
    }

    public AnnouncementNotFoundException(String message){
        super(message);
    }
}
