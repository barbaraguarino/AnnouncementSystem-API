package com.system.announcement.dtos.announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record requestAnnouncementRecordDTO(
        @NotBlank String title,
        @NotBlank String content,
        @NotNull String city,
        @NotNull Set<String> categories,
        String imageArchive,
        float price
) {
}
