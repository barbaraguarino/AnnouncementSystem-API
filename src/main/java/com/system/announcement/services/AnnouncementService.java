package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.dtos.Announcement.SaveAnnouncementDTO;
import com.system.announcement.dtos.Announcement.requestFilterAnnouncementRecordDTO;
import com.system.announcement.dtos.Announcement.AnnouncementDTO;
import com.system.announcement.exceptions.AnnouncementNotFoundException;
import com.system.announcement.exceptions.WithoutAuthorizationException;
import com.system.announcement.infra.specifications.AnnouncementSpecification;
import com.system.announcement.models.Announcement;
import com.system.announcement.repositories.AnnouncementRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@Transactional
public class AnnouncementService {

    private final AuthDetails authDetails;
    private final AnnouncementRepository announcementRepository;
    private final CityService cityService;
    private final CategoryService categoryService;
    private final FavoriteService favoriteService;

    public AnnouncementService(AuthDetails authDetails, AnnouncementRepository announcementRepository, CityService cityService, CategoryService categoryService, FavoriteService favoriteService) {
        this.authDetails = authDetails;
        this.announcementRepository = announcementRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.favoriteService = favoriteService;
    }

    public AnnouncementDTO save(@Valid SaveAnnouncementDTO requestDTO) {
        var user = authDetails.getAuthenticatedUser();
        var announcement = new Announcement();

        announcement.setTitle(requestDTO.title());
        announcement.setContent(requestDTO.content());
        if(requestDTO.price() != 0.0f) announcement.setPrice(requestDTO.price());
        announcement.setCity(cityService.getById(requestDTO.city()));
        announcement.setCategories(categoryService.getAllById(requestDTO.categories()));
        announcement.setAuthor(user);
        if(requestDTO.imageArchive() != null && !requestDTO.imageArchive().isEmpty()) announcement.setImageArchive(requestDTO.imageArchive());
        announcement = announcementRepository.save(announcement);
        return new AnnouncementDTO(announcement);

    }

    public Page<AnnouncementDTO> findAllWithFilter(requestFilterAnnouncementRecordDTO filterDTO, Pageable pageable) {
        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("date")));
        Page<Announcement> announcements = announcementRepository.findAll(new AnnouncementSpecification(filterDTO), pageableWithSorting);
        return announcements.map(AnnouncementDTO::new);
    }

    public AnnouncementDTO findById(UUID id){
        var optional = announcementRepository.findById(id);
        if(optional.isPresent()) return new AnnouncementDTO(optional.get());
        throw new AnnouncementNotFoundException();
    }

    public Page<AnnouncementDTO> findAllClosed(Pageable pageable){
        Page<Announcement> announcements = announcementRepository.findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(), AnnouncementStatus.CLOSED, pageable);
        return announcements.map(AnnouncementDTO::new);
    }

    public Page<AnnouncementDTO> findAllSuspended(Pageable pageable) {
        Page<Announcement> announcements = announcementRepository.findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(), AnnouncementStatus.SUSPENDED, pageable);
        return announcements.map(AnnouncementDTO::new);
    }

    public Page<AnnouncementDTO> findAllOpen(Pageable pageable){
        Page<Announcement> announcements = announcementRepository.findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(), AnnouncementStatus.VISIBLE, pageable);
        return announcements.map(AnnouncementDTO::new);
    }

    public AnnouncementDTO editById(@Valid @NotNull SaveAnnouncementDTO editAnnouncementDTO, @NotNull UUID id) {
        var announcementOptional = announcementRepository.findById(id);
        if(announcementOptional.isEmpty()) throw new AnnouncementNotFoundException();
        var announcement = announcementOptional.get();

        announcement.setTitle(editAnnouncementDTO.title());
        announcement.setContent(editAnnouncementDTO.content());
        if(editAnnouncementDTO.price() != 0.0f) announcement.setPrice(editAnnouncementDTO.price());
        announcement.setCity(cityService.getById(editAnnouncementDTO.city()));
        announcement.setCategories(categoryService.getAllById(editAnnouncementDTO.categories()));
        if(editAnnouncementDTO.imageArchive() != null && !editAnnouncementDTO.imageArchive().isEmpty()) announcement.setImageArchive(editAnnouncementDTO.imageArchive());
        return new AnnouncementDTO(announcementRepository.save(announcement));
    }

    public Announcement getById(UUID id){
        var optional = announcementRepository.findById(id);
        if(optional.isEmpty()) throw new AnnouncementNotFoundException();
        return optional.get();
    }

    public void delete(@Valid UUID id) {
        var optional = announcementRepository.findById(id);
        if(optional.isEmpty()) throw new AnnouncementNotFoundException();
        var announcement = optional.get();

        if(announcement.getAuthor().getEmail().equals(authDetails.getAuthenticatedUser().getEmail())) throw new WithoutAuthorizationException();

        announcement.setStatus(AnnouncementStatus.DELETED);
        announcement.setDeletionDate(new Timestamp(System.currentTimeMillis()));
        announcementRepository.save(announcement);

        favoriteService.deleteAll(announcement);
    }
}
