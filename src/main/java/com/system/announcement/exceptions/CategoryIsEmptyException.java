package com.system.announcement.exceptions;

public class CategoryIsEmptyException extends RuntimeException {

    public CategoryIsEmptyException(){
        super("Category is Empty");
    }

    public CategoryIsEmptyException(String message){
        super(message);
    }
}
