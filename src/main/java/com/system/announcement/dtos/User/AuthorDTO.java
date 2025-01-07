package com.system.announcement.dtos.user;

import com.system.announcement.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorDTO(
        @NotBlank String email,
        @NotBlank String name,
        String icon,
        @NotNull float score
) {
    public AuthorDTO(User author) {
        this(author.getEmail(), author.getName(), author.getIcon(), author.getScore());
    }
}
