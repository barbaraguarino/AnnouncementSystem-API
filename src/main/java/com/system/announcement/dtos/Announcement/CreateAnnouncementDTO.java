package com.system.announcement.dtos.Announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record CreateAnnouncementDTO(
        @NotBlank String title,
        @NotBlank String content,
        @NotNull UUID city,
        @NotNull Set<UUID> categories,
        String imageArchive,
        float price
) {
}
