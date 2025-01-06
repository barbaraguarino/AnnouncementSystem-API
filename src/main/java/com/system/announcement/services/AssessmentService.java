package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.assessment.AssessmentDTO;
import com.system.announcement.dtos.assessment.CreateAssessmentDTO;
import com.system.announcement.exceptions.AssessmentAlreadyDoneException;
import com.system.announcement.exceptions.NoAuthorizationException;
import com.system.announcement.models.Assessment;
import com.system.announcement.repositories.AssessmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AuthDetails authDetails;
    private final UserService userService;
    private final ChatService chatService;

    public AssessmentService(AssessmentRepository assessmentRepository, AuthDetails authDetails, UserService userService, ChatService chatService) {
        this.assessmentRepository = assessmentRepository;
        this.authDetails = authDetails;
        this.userService = userService;
        this.chatService = chatService;
    }

    public void createAssessment(CreateAssessmentDTO assessmentDTO) {
        var chat = chatService.findById(assessmentDTO.chat());
        var user = authDetails.getAuthenticatedUser();
        if(user.getEmail().equals(chat.getUser().getEmail())) {
            if(chat.getIsEvaluatedByUser()) throw new AssessmentAlreadyDoneException();

            var assessment = new Assessment(assessmentDTO.title(),
                    assessmentDTO.description(), assessmentDTO.grade(),
                    user, chat.getAdvertiser(), chat);
            assessment = assessmentRepository.save(assessment);

            var rateUser = chat.getAdvertiser();
            rateUser.newAssessment(assessment.getGrade());
            userService.save(rateUser);

            chat.setIsEvaluatedByUser(true);
            chatService.save(chat);
        }else if(user.getEmail().equals(chat.getAdvertiser().getEmail())) {
            if(chat.getIsEvaluatedByAdvertiser()) throw new AssessmentAlreadyDoneException();

            var assessment = new Assessment(assessmentDTO.title(),
                    assessmentDTO.description(), assessmentDTO.grade(),
                    chat.getAdvertiser(), user, chat);
            assessment = assessmentRepository.save(assessment);

            user.newAssessment(assessment.getGrade());
            userService.save(user);

            chat.setIsEvaluatedByUser(true);
            chatService.save(chat);

        }else throw new NoAuthorizationException();
    }

    public Page<AssessmentDTO> getMyAssessments(Pageable pageable) {
        var user = authDetails.getAuthenticatedUser();
        Pageable pageableWithSort = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "date")
        );
        var assessments = assessmentRepository.findAllByRatedUser(user, pageableWithSort);
        return assessments.map((assessment -> new AssessmentDTO(assessment, user)));

    }

    public Page<AssessmentDTO> getMyReviews(Pageable pageable) {
        var user = authDetails.getAuthenticatedUser();
        Pageable pageableWithSort = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "date")
        );
        var reviews = assessmentRepository.findAllByEvaluatorUser(user, pageableWithSort);
        return reviews.map((review -> new AssessmentDTO(review, user)));

    }
}
