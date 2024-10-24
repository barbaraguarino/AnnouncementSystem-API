package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.requestAnnouncementRecordDTO;
import com.system.announcement.dtos.responseOneAnnouncementRecordDTO;
import com.system.announcement.models.Announcement;
import com.system.announcement.repositories.AnnouncementRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AnnouncementService {

    private final AuthDetails authDetails;
    private final AnnouncementRepository announcementRepository;
    private final CityService cityService;
    private final CategoryService categoryService;

    public AnnouncementService(AuthDetails authDetails, AnnouncementRepository announcementRepository, CityService cityService, CategoryService categoryService) {
        this.authDetails = authDetails;
        this.announcementRepository = announcementRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
    }

    public responseOneAnnouncementRecordDTO save(@Valid requestAnnouncementRecordDTO requestDTO) {
        var user = authDetails.getAuthenticatedUser();
        var announcement = new Announcement();

        announcement.setTitle(requestDTO.title());
        announcement.setContent(requestDTO.content());
        if(requestDTO.price() != 0.0f) announcement.setPrice(requestDTO.price());
        announcement.setCity(cityService.getById(requestDTO.city()));
        announcement.setCategories(categoryService.getAllById(requestDTO.categories()));
        announcement.setAuthor(user);

        announcement = announcementRepository.save(announcement);

        return new responseOneAnnouncementRecordDTO(announcement);

    }
}
