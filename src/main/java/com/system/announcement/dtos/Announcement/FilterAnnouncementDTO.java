package com.system.announcement.dtos.announcement;

import com.system.announcement.auxiliary.enums.UserType;

import java.util.Set;
import java.util.UUID;

public record FilterAnnouncementDTO(
        String title,
        String content,
        Set<UUID> cities,
        Set<UUID> categories,
        Float minPrice,
        Float maxPrice,
        UserType userType
) {}
