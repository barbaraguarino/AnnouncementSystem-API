package com.system.announcement.infra.specifications;

import com.system.announcement.auxiliary.enums.ChatStatus;
import com.system.announcement.models.Chat;
import com.system.announcement.models.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ChatSpecification implements Specification<Chat> {

    private final ChatStatus status;
    private final User user;

    public ChatSpecification(User user) {
        this.status = ChatStatus.DELETED;
        this.user = user;
    }

    @Override
    public Predicate toPredicate(Root<Chat> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        Predicate userPredicate = criteriaBuilder.or(
                criteriaBuilder.equal(root.get("user"), user),
                criteriaBuilder.equal(root.get("advertiser"), user)
        );
        predicates.add(userPredicate);

        predicates.add(criteriaBuilder.notEqual(root.get("status"), status));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
