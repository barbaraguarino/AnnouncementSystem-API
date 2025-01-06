package com.system.announcement.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message){
        super(message);
    }

    public CategoryNotFoundException(){
        super("Categoria não encontrada. Por favor verifique os dados passados novamente.");
    }

}
