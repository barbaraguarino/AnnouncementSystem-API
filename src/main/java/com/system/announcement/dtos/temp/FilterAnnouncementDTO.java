package com.system.announcement.dtos.announcement;

import com.system.announcement.auxiliary.enums.UserType;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;
import java.util.UUID;

public record FilterAnnouncementDTO(
        String title,
        String content,
        Set<UUID> cities,
        Set<UUID> categories,
        @PositiveOrZero Float minPrice,
        @PositiveOrZero Float maxPrice,
        UserType userType
) {}
