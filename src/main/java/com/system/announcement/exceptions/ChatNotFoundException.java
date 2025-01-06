package com.system.announcement.exceptions;

public class ChatNotFoundException extends RuntimeException {

    public ChatNotFoundException(String message) {
        super(message);
    }

    public ChatNotFoundException(){
        super("Chat não encontrado. Por favor verifique os dados do chat.");
    }
}