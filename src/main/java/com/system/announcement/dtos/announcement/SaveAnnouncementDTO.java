package com.system.announcement.dtos.announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;
import java.util.UUID;

public record SaveAnnouncementDTO(
        @NotBlank String title,
        @NotBlank String content,
        @NotNull UUID city,
        @NotNull Set<UUID> categories,
        String imageArchive,
        @PositiveOrZero float price
) {
}
