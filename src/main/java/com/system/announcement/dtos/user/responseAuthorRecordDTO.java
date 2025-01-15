package com.system.announcement.dtos.User;

import com.system.announcement.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record responseAuthorRecordDTO(
        @NotBlank String email,
        @NotBlank String name,
        String icon,
        @NotNull float score
) {
    public responseAuthorRecordDTO(User author) {
        this(author.getEmail(), author.getName(), author.getIcon(), author.getScore());
    }
}
