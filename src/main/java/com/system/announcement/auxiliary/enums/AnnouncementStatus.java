package com.system.announcement.auxiliary.enums;

import lombok.Getter;

@Getter
public enum AnnouncementStatus {

    VISIBLE("Visível"),
    CLOSED("Fechado"),
    SUSPENDED("Suspenso"),
    DELETED("Excluído");

    private final String status;

    AnnouncementStatus(String status){
        this.status = status;
    }

}
