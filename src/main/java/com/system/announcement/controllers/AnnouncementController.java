package com.system.announcement.controllers;

import com.system.announcement.dtos.announcement.SaveAnnouncementDTO;
import com.system.announcement.services.AnnouncementService;
import com.system.announcement.dtos.announcement.requestFilterAnnouncementRecordDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<Object> createAnnouncement(@RequestBody @Valid SaveAnnouncementDTO saveAnnouncementDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(announcementService.save(saveAnnouncementDTO));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Object> editAnnouncement(@RequestBody @Valid SaveAnnouncementDTO saveAnnouncementDTO, @PathVariable @NotNull UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(announcementService.editById(saveAnnouncementDTO, id));
    }

    @PostMapping("/filter")
    public ResponseEntity<Object> filterAnnouncements(@RequestBody @Valid requestFilterAnnouncementRecordDTO filterDTO, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(announcementService.findAllWithFilter(filterDTO, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnnouncementById(@Valid @PathVariable UUID id) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAnnouncement(@PathVariable @Valid UUID id) {
        announcementService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
