package com.system.announcement.repositories;

import com.system.announcement.auxiliary.id.FavoriteId;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.Favorite;
import com.system.announcement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    Boolean existsFavoriteByAnnouncementAndUser(Announcement announcement, User user);

    Page<Favorite> getAllByUser(User user, Pageable pageable);

    void deleteByAnnouncementAndUser(Announcement announcement, User user);

    void deleteAllByAnnouncement(Announcement announcement);

}
