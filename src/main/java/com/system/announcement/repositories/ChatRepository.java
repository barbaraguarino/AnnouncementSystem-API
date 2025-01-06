package com.system.announcement.repositories;

import com.system.announcement.models.Announcement;
import com.system.announcement.models.Chat;
import com.system.announcement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID>, JpaSpecificationExecutor<Chat> {
    Optional<Chat> findChatByAnnouncementAndAdvertiser(Announcement announcement, User advertiser);

}

