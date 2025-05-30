package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.dtos.announcement.FilterAnnouncementDTO;
import com.system.announcement.dtos.announcement.SaveAnnouncementDTO;
import com.system.announcement.dtos.announcement.AnnouncementDTO;
import com.system.announcement.exceptions.AnnouncementIsClosedException;
import com.system.announcement.exceptions.AnnouncementIsDeletedException;
import com.system.announcement.exceptions.AnnouncementNotFoundException;
import com.system.announcement.exceptions.WithoutAuthorizationException;
import com.system.announcement.infra.specifications.AnnouncementSpecification;
import com.system.announcement.models.Announcement;
import com.system.announcement.repositories.AnnouncementRepository;
import com.system.announcement.repositories.ChatRepository;
import com.system.announcement.repositories.FavoriteRepository;
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
    private final FavoriteRepository favoriteRepository;
    private final ChatRepository chatRepository;
    private final UserService userService;

    public AnnouncementService(AuthDetails authDetails,
                               AnnouncementRepository announcementRepository,
                               CityService cityService,
                               CategoryService categoryService,
                               FavoriteRepository favoriteRepository,
                               ChatRepository chatRepository, UserService userService) {
        this.authDetails = authDetails;
        this.announcementRepository = announcementRepository;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.favoriteRepository = favoriteRepository;
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public AnnouncementDTO save(@Valid SaveAnnouncementDTO requestDTO) {
        var user = authDetails.getAuthenticatedUser();
        var announcement = new Announcement();

        announcement.setTitle(requestDTO.title());
        announcement.setContent(requestDTO.content());
        announcement.setPrice(requestDTO.price());
        announcement.setCity(cityService.getById(requestDTO.city()));
        announcement.setCategories(categoryService.getAllById(requestDTO.categories()));
        announcement.setAuthor(user);

        if(requestDTO.imageArchive() != null && !requestDTO.imageArchive().isEmpty())
            announcement.setImageArchive(requestDTO.imageArchive());

        announcement = announcementRepository.save(announcement);

        return new AnnouncementDTO(announcement);

    }

    public Page<AnnouncementDTO> findAllWithFilter(@Valid FilterAnnouncementDTO filterDTO,
                                                   Pageable pageable) {
        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), Sort.by(Sort.Order.desc("date")));

        Page<Announcement> announcements = announcementRepository
                .findAll(new AnnouncementSpecification(filterDTO), pageableWithSorting);

        return announcements.map(AnnouncementDTO::new);
    }

    public AnnouncementDTO findById(@NotNull UUID id){
        var optional = announcementRepository.findOneByIdAndStatusIsNot(id, AnnouncementStatus.DELETED);

        if(optional.isPresent()) return new AnnouncementDTO(optional.get());

        throw new AnnouncementNotFoundException();
    }

    public Page<AnnouncementDTO> findAllClosed(Pageable pageable){

        Page<Announcement> announcements = announcementRepository
                .findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(),
                        AnnouncementStatus.CLOSED, pageable);

        return announcements.map(AnnouncementDTO::new);
    }

    public Page<AnnouncementDTO> findAllSuspended(Pageable pageable) {

        Page<Announcement> announcements = announcementRepository
                .findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(),
                        AnnouncementStatus.SUSPENDED, pageable);

        return announcements.map(AnnouncementDTO::new);
    }

    public Page<AnnouncementDTO> findAllOpen(Pageable pageable){

        Page<Announcement> announcements = announcementRepository
                .findAllByAuthorAndStatus(authDetails.getAuthenticatedUser(),
                        AnnouncementStatus.VISIBLE, pageable);

        return announcements.map(AnnouncementDTO::new);
    }

    public AnnouncementDTO editById(@Valid SaveAnnouncementDTO editAnnouncementDTO,
                                    @NotNull UUID id) {

        var announcement = this.getById(id);
        var user = authDetails.getAuthenticatedUser();

        if(!announcement.getAuthor().getEmail().equals(user.getEmail()))
            throw new WithoutAuthorizationException();

        if(announcement.getStatus().equals(AnnouncementStatus.DELETED))
            throw new AnnouncementIsDeletedException();

        if(announcement.getStatus().equals(AnnouncementStatus.CLOSED))
            throw new AnnouncementIsClosedException();

        announcement.setTitle(editAnnouncementDTO.title());
        announcement.setContent(editAnnouncementDTO.content());
        announcement.setPrice(editAnnouncementDTO.price());
        announcement.setCity(cityService.getById(editAnnouncementDTO.city()));
        announcement.setCategories(categoryService.getAllById(editAnnouncementDTO.categories()));

        if(editAnnouncementDTO.imageArchive() != null && !editAnnouncementDTO.imageArchive().isEmpty())
            announcement.setImageArchive(editAnnouncementDTO.imageArchive());

        return new AnnouncementDTO(announcementRepository.save(announcement));
    }

    public Announcement getById(UUID id){
        var optional = announcementRepository.findById(id);

        if(optional.isEmpty()) throw new AnnouncementNotFoundException();
        return optional.get();
    }

    public void delete(UUID id) {
        var announcement = this.getById(id);

        if (!announcement.getAuthor().getEmail()
                .equals(authDetails.getAuthenticatedUser().getEmail()))
            throw new WithoutAuthorizationException();

        announcement.setStatus(AnnouncementStatus.DELETED);
        announcement.setDeletionDate(new Timestamp(System.currentTimeMillis()));
        announcement = announcementRepository.save(announcement);

        favoriteRepository.deleteAllByAnnouncement(announcement);

        var chats = announcement.getChats();
        chats.forEach(chat -> {
            chat.delete();
            chatRepository.save(chat);
        });
    }

    public Page<AnnouncementDTO> getByAuthor(@Valid String email, Pageable pageable) {

        var user = userService.getUserByEmail(email);

        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), Sort.by(Sort.Order.desc("date")));

        var announcements = announcementRepository.findAllByAuthorAndStatus(user, AnnouncementStatus.VISIBLE, pageableWithSorting);

        return announcements.map(AnnouncementDTO::new);

    }

    public AnnouncementDTO closeAnnouncement(@Valid UUID id) {
        var user = authDetails.getAuthenticatedUser();
        var announcement = this.getById(id);
        if(!announcement.getAuthor().getEmail().equals(user.getEmail()))
            throw new WithoutAuthorizationException();
        if(announcement.getStatus().equals(AnnouncementStatus.CLOSED))
            throw new AnnouncementIsClosedException();

        announcement.setStatus(AnnouncementStatus.CLOSED);
        announcement = announcementRepository.save(announcement);

        favoriteRepository.deleteAllByAnnouncement(announcement);

        var chats = announcement.getChats();
        chats.forEach(chat -> {
            chat.close();
            chatRepository.save(chat);
        });

        return new AnnouncementDTO(announcement);

    }
}
