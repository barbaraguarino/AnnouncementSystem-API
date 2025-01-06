package com.system.announcement.dtos.user;

import com.system.announcement.dtos.announcement.AnnouncementDTO;
import com.system.announcement.dtos.assessment.AssessmentDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Set;

public record UserDTO(
        @NotBlank String email,
        @NotBlank String icon,
        @NotBlank String type,
        @NotNull float score,
        @NotNull int numAssessment,
        @NotBlank String role,
        Timestamp deleteDate,
        Set<AnnouncementDTO> favorites,
        Set<AssessmentDTO> myReviews,
        Set<AssessmentDTO> assessments
) {
}
