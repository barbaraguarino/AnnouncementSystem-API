package com.system.announcement.dtos.announcement;

import com.system.announcement.dtos.user.responseAuthorRecordDTO;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.Category;
import com.system.announcement.models.City;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

public record AnnouncementDTO(
        @NotNull UUID id,
        @NotBlank String title,
        @NotBlank String content,
        @NotNull responseAuthorRecordDTO author,
        @NotNull City city,
        @NotNull Set<Category> categories,
        @NotNull Timestamp date,
        String imageArchive,
        String status,
        Timestamp deletionDate,
        float price
) {
    public AnnouncementDTO(Announcement announcement) {
        this(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                new responseAuthorRecordDTO(announcement.getAuthor()),
                announcement.getCity(),
                announcement.getCategories(),
                announcement.getDate(),
                announcement.getImageArchive(),
                announcement.getStatus().getStatus(),
                announcement.getDeletionDate(),
                announcement.getPrice()
        );
    }
}
