package com.system.announcement.infra.specifications;

import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.models.Announcement;
import com.system.announcement.models.User;
import com.system.announcement.services.requestFilterAnnouncementRecordDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementSpecification implements Specification<Announcement> {

    private final requestFilterAnnouncementRecordDTO filterDTO;
    private final AnnouncementStatus announcementStatus;
    private final User author;

    public AnnouncementSpecification(requestFilterAnnouncementRecordDTO filterDTO, AnnouncementStatus announcementStatus, User author) {
        this.filterDTO = filterDTO;
        this.announcementStatus = announcementStatus;
        this.author = author;
    }

    @Override
    public Predicate toPredicate(Root<Announcement> announcementRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filterDTO.title() != null && !filterDTO.title().isEmpty()) {
            predicates.add(criteriaBuilder.like(announcementRoot.get("title"), "%" + filterDTO.title() + "%"));
        }

        if (filterDTO.content() != null && !filterDTO.content().isEmpty()) {
            predicates.add(criteriaBuilder.like(announcementRoot.get("content"), "%" + filterDTO.content() + "%"));
        }

        if (filterDTO.cities() != null && !filterDTO.cities().isEmpty()) {
            predicates.add(announcementRoot.join("city").get("name").in(filterDTO.cities()));
        }

        if (filterDTO.categories() != null && !filterDTO.categories().isEmpty()) {
            predicates.add(announcementRoot.join("categories").get("name").in(filterDTO.categories()));
        }

        if (filterDTO.minPrice() != null) {
            predicates.add(criteriaBuilder.ge(announcementRoot.get("price"), filterDTO.minPrice()));
        }

        if (filterDTO.maxPrice() != null) {
            predicates.add(criteriaBuilder.le(announcementRoot.get("price"), filterDTO.maxPrice()));
        }

        if (announcementStatus != null) {
            predicates.add(criteriaBuilder.equal(announcementRoot.get("status"), announcementStatus));
        }

        if (author != null) {
            predicates.add(criteriaBuilder.equal(announcementRoot.get("author"), author));
        }

        if (filterDTO.userType() != null) {
            predicates.add(criteriaBuilder.equal(announcementRoot.get("author").get("type"), filterDTO.userType()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
