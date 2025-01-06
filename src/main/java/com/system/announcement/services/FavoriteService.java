package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.announcement.AnnouncementDTO;
import com.system.announcement.models.Favorite;
import com.system.announcement.repositories.FavoriteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class FavoriteService {

    private final AnnouncementService announcementService;
    private final FavoriteRepository favoriteRepository;
    private final AuthDetails authDetails;

    public FavoriteService(AnnouncementService announcementService, FavoriteRepository favoriteRepository, AuthDetails authDetails) {
        this.announcementService = announcementService;
        this.favoriteRepository = favoriteRepository;
        this.authDetails = authDetails;
    }

    public void saveFavorite(@Valid UUID idAnnouncement) {
        var announcement = announcementService.getById(idAnnouncement);
        var user = authDetails.getAuthenticatedUser();
        var favorite = new Favorite(announcement, user);
        favoriteRepository.save(favorite);
    }

    public boolean isFavorite(@Valid UUID idAnnouncement) {
        var announcement = announcementService.getById(idAnnouncement);
        var user = authDetails.getAuthenticatedUser();
        return favoriteRepository.existsFavoriteByAnnouncementAndUser(announcement, user);
    }

    public Page<AnnouncementDTO> getMyAllFavorite(Pageable pageable) {
        var user = authDetails.getAuthenticatedUser();
        Page<Favorite> favorites = favoriteRepository.getAllByUser(user, pageable);
        return favorites.map(favorite -> new AnnouncementDTO(favorite.getAnnouncement()));
    }

    public void removeFavorite(@Valid UUID idAnnouncement){
        var announcement = announcementService.getById(idAnnouncement);
        var user = authDetails.getAuthenticatedUser();
        favoriteRepository.deleteByAnnouncementAndUser(announcement, user);
    }
}
