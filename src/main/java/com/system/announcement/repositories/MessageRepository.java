package com.system.announcement.repositories;

import com.system.announcement.models.Chat;
import com.system.announcement.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByChatOrderByDateAsc(Chat chat);
}

