package com.system.announcement.auxiliary.enums;

public enum ChatStatus {

    OPEN("Aberto"),
    CLOSED("Fechado"),
    DELETED("Deletado");

    private final String status;

    ChatStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}