package com.system.announcement.dtos.user;

import com.system.announcement.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record UserDTO(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String icon,
        @NotBlank String type,
        @NotNull float score,
        @NotNull int numAssessment,
        @NotBlank String role,
        Timestamp deleteDate
) {
    public UserDTO(User user) {
        this(user.getEmail(), user.getName(), user.getIcon(), user.getType().getType(), user.getScore(), user.getNumAssessment(),
                user.getRole().getRole(), user.getDeletedDate());
    }
}
