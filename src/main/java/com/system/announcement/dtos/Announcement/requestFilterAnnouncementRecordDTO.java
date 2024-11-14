package com.system.announcement.dtos.Announcement;

import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.auxiliary.enums.UserType;

import java.util.Set;
import java.util.UUID;

public record requestFilterAnnouncementRecordDTO(
        String title,
        String content,
        Set<UUID> cities,
        Set<UUID> categories,
        Float minPrice,
        Float maxPrice,
        UserType userType
) {}
