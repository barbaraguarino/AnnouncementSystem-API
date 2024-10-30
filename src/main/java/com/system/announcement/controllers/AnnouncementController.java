package com.system.announcement.controllers;

import com.system.announcement.dtos.Announcement.requestAnnouncementRecordDTO;
import com.system.announcement.services.AnnouncementService;
import com.system.announcement.services.requestFilterAnnouncementRecordDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAnnouncement(@RequestBody @Valid requestAnnouncementRecordDTO requestAnnouncementRecordDTO) {
        var responseAnnouncement = announcementService.save(requestAnnouncementRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseAnnouncement);
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> filterAnnouncements(@RequestBody @Valid requestFilterAnnouncementRecordDTO filterDTO, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.findAllWithFilter(filterDTO, pageable));
    }
}
