package com.system.announcement.exceptions;

public class AssessmentAlreadyDoneException extends RuntimeException {

    public AssessmentAlreadyDoneException() {
        super("Assessment already done");
    }

    public AssessmentAlreadyDoneException(String message) {
        super(message);
    }
}
