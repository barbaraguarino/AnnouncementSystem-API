package com.system.announcement.controllers;

import com.system.announcement.dtos.assessment.CreateAssessmentDTO;
import com.system.announcement.services.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<Object> createAssessment(@RequestBody CreateAssessmentDTO assessmentDTO) {
        assessmentService.createAssessment(assessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/reviews")
    public ResponseEntity<Object> getMyReviews(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(assessmentService.getMyReviews(pageable));
    }

    @GetMapping("/assessments/{email}")
    public ResponseEntity<Object> getMyAssessments(@PathVariable @Valid String email, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(assessmentService.getMyAssessments(email, pageable));
    }

}
