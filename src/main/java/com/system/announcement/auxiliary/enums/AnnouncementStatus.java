package com.system.announcement.auxiliary.enums;

public enum AnnouncementStatus {

    VISIBLE("Suspenso"),
    SUSPENDED("Excluido"),
    DELETED("Visível");

    private final String status;

    AnnouncementStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
