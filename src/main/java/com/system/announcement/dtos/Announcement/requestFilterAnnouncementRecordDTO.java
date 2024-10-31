package com.system.announcement.dtos.Announcement;

import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.auxiliary.enums.UserType;

import java.util.Set;

public record requestFilterAnnouncementRecordDTO(
        String title,
        String content,
        Set<String> cities,
        Set<String> categories,
        Float minPrice,
        Float maxPrice,
        UserType userType,
        AnnouncementStatus announcementStatus
) {}
