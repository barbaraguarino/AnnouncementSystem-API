package com.system.announcement.dtos.assessment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAssessmentDTO(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull float grade,
        @NotNull UUID chat
){}
