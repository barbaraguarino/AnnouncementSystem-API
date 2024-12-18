package com.system.announcement.repositories;

import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID>, JpaSpecificationExecutor<Announcement> {
   public Page<Announcement> findAllByAuthorAndStatus(User author, AnnouncementStatus status, Pageable pageable);
}
