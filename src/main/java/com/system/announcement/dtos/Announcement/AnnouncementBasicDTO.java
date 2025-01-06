package com.system.announcement.dtos.announcement;

import com.system.announcement.models.Announcement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AnnouncementBasicDTO(
        @NotNull UUID id,
        @NotBlank String title,
        String imageArchive,
        String status
){
    public AnnouncementBasicDTO(Announcement announcement) {
        this(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getImageArchive(),
                announcement.getStatus().getStatus()
        );
    }
}
