package com.system.announcement.exceptions;

public class CategoryIsEmptyException extends RuntimeException {

    public CategoryIsEmptyException(){
        super("Categoria está vazia. Escolha uma ou mais categorias.");
    }
}
