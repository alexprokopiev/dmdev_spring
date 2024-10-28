package org.alexprokopiev.spring.database.repository;

import jakarta.persistence.*;
import org.alexprokopiev.spring.database.entity.*;
import org.alexprokopiev.spring.dto.PersonalInfo2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

public interface UserRepository extends JpaRepository<User, Long>,
                                        FilterUserRepository,
                                        RevisionRepository<User, Long, Integer>,
                                        QuerydslPredicateExecutor<User> {

    @Query("select u from User u where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllBy(@Param("firstname") String firstname, @Param("lastname") String lastname);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE username = :username")
    List<User> findAllByUsername(@Param("username") String username);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.role = :role where u.id in (:ids)")
    int updateRole(@Param("role") Role role, @Param("ids") Long... ids);

    Optional<User> findTopByOrderByIdDesc();

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

    // Collection, Stream (batch, close)
    // Streamable, Slice, Page
    //@EntityGraph("User.company")
    @EntityGraph(attributePaths = {"company"})
    @Query(value = "select u from User u",
            countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);

    // List<PersonalInfo> findAllByCompanyId(Integer companyId);
    // <T> List<T> findAllByCompanyId(Integer companyId, Class<T> clazz);
    @Query(nativeQuery = true,
            value = "SELECT u.firstname, u.lastname, u.birth_date birthDate FROM users u WHERE u.company_id = :companyId")
    List<PersonalInfo2> findAllByCompanyId(@Param("companyId") Integer companyId);

    Optional<User> findByUsername(String username);
}
