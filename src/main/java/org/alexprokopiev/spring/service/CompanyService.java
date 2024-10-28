package org.alexprokopiev.spring.service;

import lombok.RequiredArgsConstructor;
import org.alexprokopiev.spring.database.repository.CompanyRepository;
import org.alexprokopiev.spring.dto.CompanyReadDto;
import org.alexprokopiev.spring.listener.entity.AccessType;
import org.alexprokopiev.spring.listener.entity.EntityEvent;
import org.alexprokopiev.spring.mapper.CompanyReadMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    public final CompanyRepository companyRepository;
    public final ApplicationEventPublisher eventPublisher;
    private final CompanyReadMapper companyReadMapper;

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return companyReadMapper.map(entity);
                });
    }

    public List<CompanyReadDto> findAll() {
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }
}
