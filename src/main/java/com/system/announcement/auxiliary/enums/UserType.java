package com.system.announcement.auxiliary.enums;

public enum UserType {

    STUDENT("Aluno"),
    TEACHER("Professor"),
    EMPLOYEE("Funcionário");

    private final String type;

    UserType(String type){
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
