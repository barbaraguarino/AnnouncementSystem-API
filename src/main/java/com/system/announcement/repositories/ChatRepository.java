package com.system.announcement.repositories;

import com.system.announcement.models.Announcement;
import com.system.announcement.models.Chat;
import com.system.announcement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    List<Chat> findByUser(User user);
    List<Chat> findByAnnouncement(Announcement announcement);
    Optional<Chat> findChatByUserAndAnnouncement(User user, Announcement announcement);
}

