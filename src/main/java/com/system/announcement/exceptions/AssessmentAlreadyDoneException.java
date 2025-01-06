package com.system.announcement.exceptions;

public class AssessmentAlreadyDoneException extends RuntimeException {

    public AssessmentAlreadyDoneException() {
        super("Avaliação já realizada com sucesso.");
    }

    public AssessmentAlreadyDoneException(String message) {
        super(message);
    }
}
