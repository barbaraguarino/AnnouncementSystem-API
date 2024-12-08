package com.system.announcement.infra.specifications;

import com.system.announcement.auxiliary.enums.AnnouncementStatus;
import com.system.announcement.models.Announcement;
import com.system.announcement.dtos.Announcement.requestFilterAnnouncementRecordDTO;
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

    public AnnouncementSpecification(requestFilterAnnouncementRecordDTO filterDTO) {
        this.filterDTO = filterDTO;
        this.announcementStatus = AnnouncementStatus.VISIBLE;
    }

    @Override
    public Predicate toPredicate(Root<Announcement> announcementRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(announcementRoot.get("status"), announcementStatus));

        if (filterDTO.title() != null && !filterDTO.title().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(announcementRoot.get("title"))),
                    "%" + filterDTO.title().toLowerCase() + "%"
            ));
        }

        if (filterDTO.content() != null && !filterDTO.content().isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(announcementRoot.get("content"))),
                    "%" + filterDTO.content().toLowerCase() + "%"
            ));
        }

        if (filterDTO.cities() != null && !filterDTO.cities().isEmpty()) {
            predicates.add(announcementRoot.join("city").get("id").in(filterDTO.cities()));
        }

        if (filterDTO.categories() != null && !filterDTO.categories().isEmpty()) {
            predicates.add(announcementRoot.join("categories").get("id").in(filterDTO.categories()));
        }

        if (filterDTO.minPrice() != null) {
            predicates.add(criteriaBuilder.ge(announcementRoot.get("price"), filterDTO.minPrice()));
        }

        if (filterDTO.maxPrice() != null) {
            predicates.add(criteriaBuilder.le(announcementRoot.get("price"), filterDTO.maxPrice()));
        }

        if (filterDTO.userType() != null) {
            predicates.add(criteriaBuilder.equal(announcementRoot.get("author").get("type"), filterDTO.userType()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
