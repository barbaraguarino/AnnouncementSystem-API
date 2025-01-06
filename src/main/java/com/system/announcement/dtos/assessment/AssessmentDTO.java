package com.system.announcement.dtos.assessment;

import com.system.announcement.dtos.chat.ChatDTO;
import com.system.announcement.dtos.user.UserBasicDTO;
import com.system.announcement.models.Assessment;
import com.system.announcement.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.UUID;

public record AssessmentDTO(
        @NotNull UUID id,
        @NotBlank String title,
        @NotNull String description,
        @NotNull float grade,
        @NotNull Timestamp date,
        @NotNull UserBasicDTO evaluatorUser,
        @NotNull UserBasicDTO ratedUser,
        @NotNull ChatDTO chat

){
    public AssessmentDTO(Assessment assessment, User user) {
        this(
                assessment.getId(),
                assessment.getTitle(),
                assessment.getDescription(),
                assessment.getGrade(),
                assessment.getDate(),
                new UserBasicDTO(assessment.getEvaluatorUser()),
                new UserBasicDTO(assessment.getRatedUser()),
                new ChatDTO(assessment.getChat(), user)
            );
    }
}
