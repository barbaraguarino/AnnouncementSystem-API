package com.system.announcement.controllers;

import com.system.announcement.services.AnnouncementService;
import com.system.announcement.services.CategoryService;
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
}
