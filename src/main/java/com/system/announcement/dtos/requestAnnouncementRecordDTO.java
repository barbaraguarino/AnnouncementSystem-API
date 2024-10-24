package com.system.announcement.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record requestAnnouncementRecordDTO(
        @NotBlank String title,
        @NotBlank String content,
        @NotNull UUID city,
        @NotNull Set<UUID> categories,
        float price
) {
}
