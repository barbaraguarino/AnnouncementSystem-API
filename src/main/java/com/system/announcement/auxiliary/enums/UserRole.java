package com.system.announcement.auxiliary.enums;

public enum UserRole {

    USER("Usuário"),
    MODERATOR("Moderador"),
    ADMIN("Administrador");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}