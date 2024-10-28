package org.alexprokopiev.spring.database.repository;

import org.alexprokopiev.spring.database.entity.Role;
import org.alexprokopiev.spring.database.entity.User;
import org.alexprokopiev.spring.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter userFilter, Pageable pageable);

    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

    void updateCompanyAndRole(List<User> users);

    void updateCompanyAndRoleNamed(List<User> users);
}
