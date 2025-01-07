package com.system.announcement.auxiliary.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    USER("Usuário"),
    MODERATOR("Moderador"),
    ADMIN("Administrador");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

}