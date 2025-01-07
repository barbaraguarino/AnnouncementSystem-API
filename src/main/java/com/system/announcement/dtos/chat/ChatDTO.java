package com.system.announcement.dtos.chat;

import com.system.announcement.auxiliary.enums.ChatStatus;
import com.system.announcement.dtos.announcement.AnnouncementBasicDTO;
import com.system.announcement.dtos.user.UserBasicDTO;
import com.system.announcement.models.Chat;
import com.system.announcement.models.User;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.UUID;

public record ChatDTO(
        @NotNull UUID id,
        @NotNull UserBasicDTO participant,
        @NotNull AnnouncementBasicDTO announcement,
        @NotNull ChatStatus chatStatus,
        Timestamp dateLastMessage,
        @NotNull Boolean isEvaluated
){
    public ChatDTO(Chat chat, User user) {
        this(
                chat.getId(),
                (user.getEmail().equals(chat.getUser().getEmail())) ? new UserBasicDTO(chat.getAdvertiser()) : new UserBasicDTO(chat.getUser()),
                new AnnouncementBasicDTO(chat.getAnnouncement()),
                chat.getStatus(),
                chat.getDateLastMessage(),
                (user.getEmail().equals(chat.getUser().getEmail())) ? chat.getIsEvaluatedByUser() : chat.getIsEvaluatedByAdvertiser()
        );
    }
}
