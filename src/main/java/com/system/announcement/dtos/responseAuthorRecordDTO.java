package com.system.announcement.dtos;

import com.system.announcement.models.User;
import jakarta.validation.constraints.NotBlank;

public record responseAuthorRecordDTO(
        @NotBlank String email,
        @NotBlank String nome
) {
    public responseAuthorRecordDTO(User author) {
        this(author.getEmail(), author.getName());
    }
}
