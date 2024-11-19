package com.system.announcement.controllers;

import com.system.announcement.dtos.Announcement.EditAnnouncementDTO;
import com.system.announcement.dtos.Announcement.CreateAnnouncementDTO;
import com.system.announcement.services.AnnouncementService;
import com.system.announcement.dtos.Announcement.requestFilterAnnouncementRecordDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAnnouncement(@RequestBody @Valid CreateAnnouncementDTO createAnnouncementDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(announcementService.save(createAnnouncementDTO));
    }

    @PostMapping("/edit")
    public ResponseEntity<Object> editAnnouncement(@RequestBody @Valid EditAnnouncementDTO editAnnouncementDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(announcementService.editById(editAnnouncementDTO));
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> filterAnnouncements(@RequestBody @Valid requestFilterAnnouncementRecordDTO filterDTO, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.findAllWithFilter(filterDTO, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnnouncementById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.findById(id));
    }

    @GetMapping("/closed")
    public ResponseEntity<Object> getClosedAnnouncements(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.findAllClosed(pageable));
    }

    @GetMapping("/suspended")
    public ResponseEntity<Object> getSuspendedAnnouncements(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.findAllSuspended(pageable));
    }

    @GetMapping("/open")
    public ResponseEntity<Object> getOpenAnnouncements(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.findAllOpen(pageable));
    }

}
