package com.system.announcement.auxiliary.enums;

public enum MessageStatus {
    READ("Lido"),
    SENT("Enviado"),
    NOT_SENT("Não Enviado");

    private final String status;

    MessageStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
