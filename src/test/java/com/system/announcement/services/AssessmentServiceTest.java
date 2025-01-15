package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.assessment.AssessmentDTO;
import com.system.announcement.dtos.assessment.CreateAssessmentDTO;
import com.system.announcement.exceptions.AssessmentAlreadyDoneException;
import com.system.announcement.models.Assessment;
import com.system.announcement.models.Chat;
import com.system.announcement.models.User;
import com.system.announcement.repositories.AssessmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;
import java.util.function.Function;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssessmentServiceTest {

    @Mock
    private AssessmentRepository assessmentRepository;

    @Mock
    private AuthDetails authDetails;

    @Mock
    private UserService userService;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private AssessmentService assessmentService;

    @Nested
    class CreateAssessment {

        @Test
        @DisplayName("Deve lançar exceção se o usuário já avaliou o chat")
        void shouldThrowExceptionIfUserAlreadyEvaluated() {
            UUID chatId = UUID.randomUUID();
            CreateAssessmentDTO assessmentDTO = mock(CreateAssessmentDTO.class);
            Chat chat = mock(Chat.class);
            User user = mock(User.class);

            when(assessmentDTO.chat()).thenReturn(chatId);
            when(chatService.findById(chatId)).thenReturn(chat);
            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(user.getEmail()).thenReturn("user@example.com");
            when(chat.getUser()).thenReturn(user);
            when(chat.getIsEvaluatedByUser()).thenReturn(true);

            Assertions.assertThrows(AssessmentAlreadyDoneException.class, () ->
                    assessmentService.createAssessment(assessmentDTO)
            );

            verify(assessmentRepository, never()).save(any(Assessment.class));
        }
    }

    @Nested
    class GetMyAssessments {

        @Test
        @DisplayName("Deve retornar todas as avaliações feitas pelo usuário")
        void shouldReturnAllAssessmentsMadeByUser() {
            String email = "user@example.com";
            User user = mock(User.class);
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "date"));
            Page<Assessment> assessmentsPage = mock(Page.class);
            Page<AssessmentDTO> assessmentsDTOPage = mock(Page.class);

            when(userService.getUserByEmail(email)).thenReturn(user);

            when(assessmentRepository.findAllByRatedUser(user, pageable)).thenReturn(assessmentsPage);

            when(assessmentsPage.map(any(Function.class))).thenAnswer(invocation -> {
                Function<Assessment, AssessmentDTO> mapper = invocation.getArgument(0);
                return assessmentsDTOPage;
            });

            Page<AssessmentDTO> result = assessmentService.getMyAssessments(email, pageable);

            Assertions.assertNotNull(result);
            Assertions.assertSame(assessmentsDTOPage, result);

            verify(userService).getUserByEmail(email);
            verify(assessmentRepository).findAllByRatedUser(user, pageable);
        }

        @Test
        @DisplayName("Deve retornar uma página vazia caso o usuário não tenha avaliações")
        void shouldReturnEmptyPageIfUserHasNoAssessments() {
            String email = "user@example.com";
            User user = mock(User.class);
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "date"));
            Page<Assessment> emptyAssessments = Page.empty();

            when(userService.getUserByEmail(email)).thenReturn(user);

            when(assessmentRepository.findAllByRatedUser(user, pageable)).thenReturn(emptyAssessments);

            Page<AssessmentDTO> result = assessmentService.getMyAssessments(email, pageable);

            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.isEmpty());

            verify(userService).getUserByEmail(email);
            verify(assessmentRepository).findAllByRatedUser(user, pageable);
        }
    }

    @Nested
    class GetMyReviews {

        @Test
        @DisplayName("Deve retornar todas as avaliações feitas pelo usuário como avaliador")
        void shouldReturnAllReviewsMadeByUserAsEvaluator() {
            User user = mock(User.class);
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "date"));
            Page<Assessment> reviewsPage = mock(Page.class);

            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(assessmentRepository.findAllByEvaluatorUser(user, pageable)).thenReturn(reviewsPage);
            when(reviewsPage.map(any())).thenReturn(mock(Page.class));

            Page<AssessmentDTO> result = assessmentService.getMyReviews(pageable);

            Assertions.assertNotNull(result);
            verify(assessmentRepository).findAllByEvaluatorUser(user, pageable);
        }

        @Test
        @DisplayName("Deve retornar uma página vazia caso o usuário não tenha avaliações como avaliador")
        void shouldReturnEmptyPageIfUserHasNoReviewsAsEvaluator() {
            User user = mock(User.class);
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "date"));
            Page<Assessment> emptyReviews = Page.empty();

            when(authDetails.getAuthenticatedUser()).thenReturn(user);
            when(assessmentRepository.findAllByEvaluatorUser(user, pageable)).thenReturn(emptyReviews);

            Page<AssessmentDTO> result = assessmentService.getMyReviews(pageable);

            Assertions.assertTrue(result.isEmpty());
            verify(assessmentRepository).findAllByEvaluatorUser(user, pageable);
        }
    }
}
