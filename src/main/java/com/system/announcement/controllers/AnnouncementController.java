package com.system.announcement.controllers;

import com.system.announcement.dtos.Announcement.requestAnnouncementRecordDTO;
import com.system.announcement.services.AnnouncementService;
import com.system.announcement.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final CategoryService categoryService;

    public AnnouncementController(AnnouncementService announcementService, CategoryService categoryService) {
        this.announcementService = announcementService;
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAnnouncement(@RequestBody @Valid requestAnnouncementRecordDTO requestAnnouncementRecordDTO) {
        var responseAnnouncement = announcementService.save(requestAnnouncementRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseAnnouncement);
    }
}
