package org.alexprokopiev.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.alexprokopiev.spring.database.entity.User;
import org.alexprokopiev.spring.dto.CompanyReadDto;
import org.alexprokopiev.spring.dto.UserReadDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto map(User object) {

        CompanyReadDto companyReadDto = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);

        return new UserReadDto(object.getId(),
                                object.getUsername(),
                                object.getBirthDate(),
                                object.getFirstname(),
                                object.getLastname(),
                                object.getImage(),
                                object.getRole(),
                                companyReadDto);
    }
}
