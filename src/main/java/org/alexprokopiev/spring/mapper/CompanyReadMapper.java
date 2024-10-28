package org.alexprokopiev.spring.mapper;

import org.alexprokopiev.spring.database.entity.Company;
import org.alexprokopiev.spring.dto.CompanyReadDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto map(Company object) {
        return new CompanyReadDto(object.getId(),
                                    object.getName());
    }
}
