package com.system.announcement.repositories;

import com.system.announcement.models.Assessment;
import com.system.announcement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {
    Page<Assessment> findAllByEvaluatorUser(User evaluatorUser, Pageable pageable);
    Page<Assessment> findAllByRatedUser(User ratedUser, Pageable pageable);
}
