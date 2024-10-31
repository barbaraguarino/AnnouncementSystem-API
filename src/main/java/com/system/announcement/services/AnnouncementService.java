package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.dtos.Announcement.requestAnnouncementRecordDTO;
import com.system.announcement.dtos.Announcement.requestFilterAnnouncementRecordDTO;
import com.system.announcement.dtos.Announcement.responseOneAnnouncementRecordDTO;
import com.system.announcement.infra.specifications.AnnouncementSpecification;
import com.system.announcement.models.Announcement;
import com.system.announcement.repositories.AnnouncementRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AnnouncementService {

    private final AuthDetails authDetails;
    private final AnnouncementRepository announcementRepository;
    private final CityService cityService;
    private final CategoryService categoryService;
    private final FileService fileService;

    public AnnouncementService(AuthDetails authDetails, AnnouncementRepository announcementRepository, CityService cityService, CategoryService categoryService, FileService fileService) {
        this.authDetails = authDetails;
        this.announcementRepository = announcementRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.fileService = fileService;
    }

    public responseOneAnnouncementRecordDTO save(@Valid requestAnnouncementRecordDTO requestDTO) {
        var user = authDetails.getAuthenticatedUser();
        var announcement = new Announcement();

        announcement.setTitle(requestDTO.title());
        announcement.setContent(requestDTO.content());
        if(requestDTO.price() != 0.0f) announcement.setPrice(requestDTO.price());
        announcement.setCity(cityService.getOrSave(requestDTO.city()));
        announcement.setCategories(categoryService.getAllOrSave(requestDTO.categories()));
        announcement.setAuthor(user);
        announcement = announcementRepository.save(announcement);
        if(requestDTO.paths() != null && !requestDTO.paths().isEmpty()) announcement.setFiles(fileService.createObjectsFile(requestDTO.paths(), announcement));

        return new responseOneAnnouncementRecordDTO(announcement);

    }

    public Page<responseOneAnnouncementRecordDTO> findAllWithFilter(requestFilterAnnouncementRecordDTO filterDTO, Pageable pageable) {
        Page<Announcement> announcements = announcementRepository.findAll(new AnnouncementSpecification(filterDTO, AnnouncementStatus.VISIBLE, null), pageable);
        return announcements.map(responseOneAnnouncementRecordDTO::new);
    }

}
