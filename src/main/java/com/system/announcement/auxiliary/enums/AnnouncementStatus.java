package com.system.announcement.auxiliary.enums;

public enum AnnouncementStatus {

    VISIBLE("Visível"),
    CLOSED("Fechado"),
    SUSPENDED("Suspenso"),
    DELETED("Excluido");

    private final String status;

    AnnouncementStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
