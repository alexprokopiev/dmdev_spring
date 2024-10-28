package org.alexprokopiev.spring.database.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.alexprokopiev.spring.database.entity.Role;
import org.alexprokopiev.spring.database.entity.User;
import org.alexprokopiev.spring.database.querydsl.QPredicates;
import org.alexprokopiev.spring.dto.PersonalInfo;
import org.alexprokopiev.spring.dto.UserFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

import static org.alexprokopiev.spring.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    public static final String FIND_BY_COMPANY_AND_ROLE =
            "SELECT u.firstname, u.lastname, u.birth_date FROM users u WHERE u.company_id = ? AND u.role = ?";

    public static final String UPDATE_COMPANY_AND_ROLE =
            "UPDATE users SET company_id = ?, role = ? WHERE id = ?";

    public static final String UPDATE_COMPANY_AND_ROLE_NAMED =
            "UPDATE users SET company_id = :companyId, role = :role WHERE id = :id";

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public List<User> findAllByFilter(UserFilter userFilter, Pageable pageable) {

//        var predicate = QPredicates.builder()
//                .add(userFilter.firstname(), user.firstname::containsIgnoreCase)
//                .add(userFilter.lastname(), user.lastname::containsIgnoreCase)
//                .add(userFilter.birthDate(), user.birthDate::before)
//                .build();
//
//        return new JPAQuery<User>()
//                .select(user)
//                .from(user)
//                .where(predicate)
//                .fetch();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);
        criteria.select(user);
        List<Predicate> predicates = new ArrayList<>();
        if (userFilter.firstname() != null && !userFilter.firstname().isEmpty()) {
            predicates.add(cb.like(cb.upper(user.get("firstname")), "%" + userFilter.firstname().toUpperCase() + "%"));
        }
        if (userFilter.lastname() != null && !userFilter.lastname().isEmpty()) {
            predicates.add(cb.like(cb.upper(user.get("lastname")), "%" + userFilter.lastname().toUpperCase() + "%"));
        }
        if (userFilter.birthDate() != null) {
            predicates.add(cb.lessThan(user.get("birthDate"), userFilter.birthDate()));
        }
        criteria.where(predicates.toArray(Predicate[]::new));
        return entityManager.createQuery(criteria)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize()).getResultList();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE, (rs, rowNum) -> new PersonalInfo(
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getDate("birth_date").toLocalDate()
        ), companyId, role.name());
    }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        List<Object[]> args = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()}).toList();
        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
    }

    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        MapSqlParameterSource[] args = users.stream()
                .map(user -> Map.of(
                        "companyId", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()
                ))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);
        namedJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);
    }
}
