package com.system.announcement.auxiliary.enums;

import lombok.Getter;

@Getter
public enum UserType {

    STUDENT("Aluno"),
    TEACHER("Professor"),
    EMPLOYEE("Funcionário");

    private final String type;

    UserType(String type){
        this.type = type;
    }

}
