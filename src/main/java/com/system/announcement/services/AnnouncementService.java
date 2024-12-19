package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.dtos.announcement.requestAnnouncementRecordDTO;
import com.system.announcement.dtos.announcement.requestFilterAnnouncementRecordDTO;
import com.system.announcement.dtos.announcement.responseOneAnnouncementRecordDTO;
import com.system.announcement.exceptions.AnnouncementNotFoundException;
import com.system.announcement.infra.specifications.AnnouncementSpecification;
import com.system.announcement.models.Announcement;
import com.system.announcement.repositories.AnnouncementRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        announcement.setCity(cityService.getOrSave(requestDTO.city()));
        announcement.setCategories(categoryService.getAllOrSave(requestDTO.categories()));
        announcement.setAuthor(user);
        if(requestDTO.imageArchive() != null && !requestDTO.imageArchive().isEmpty()) announcement.setImageArchive(requestDTO.imageArchive());
        announcement = announcementRepository.save(announcement);
        return new responseOneAnnouncementRecordDTO(announcement);

    }

    public Page<responseOneAnnouncementRecordDTO> findAllWithFilter(requestFilterAnnouncementRecordDTO filterDTO, Pageable pageable) {
        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("date")));
        Page<Announcement> announcements = announcementRepository.findAll(new AnnouncementSpecification(filterDTO), pageableWithSorting);
        return announcements.map(responseOneAnnouncementRecordDTO::new);
    }

    public responseOneAnnouncementRecordDTO findById(UUID id){
        var optional = announcementRepository.findById(id);
        if(optional.isPresent()) return new responseOneAnnouncementRecordDTO(optional.get());
        throw new AnnouncementNotFoundException();
    }

    public Page<responseOneAnnouncementRecordDTO> findAllClosed(Pageable pageable){
        Page<Announcement> announcements = announcementRepository.findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(), AnnouncementStatus.CLOSED, pageable);
        return announcements.map(responseOneAnnouncementRecordDTO::new);
    }

    public Page<responseOneAnnouncementRecordDTO> findAllSuspended(Pageable pageable) {
        Page<Announcement> announcements = announcementRepository.findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(), AnnouncementStatus.SUSPENDED, pageable);
        return announcements.map(responseOneAnnouncementRecordDTO::new);
    }

    public Page<responseOneAnnouncementRecordDTO> findAllOpen(Pageable pageable){
        Page<Announcement> announcements = announcementRepository.findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(), AnnouncementStatus.VISIBLE, pageable);
        return announcements.map(responseOneAnnouncementRecordDTO::new);
    }
}
