package com.system.announcement.repositories;

import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID>,
        JpaSpecificationExecutor<Announcement> {

   Page<Announcement> findAllByAuthorAndStatus(User author,
                                               AnnouncementStatus status,
                                               Pageable pageable);

   Optional<Announcement> findOneByIdAndStatusIsNot(UUID id, AnnouncementStatus status);
}
