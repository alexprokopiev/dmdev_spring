package org.alexprokopiev.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.alexprokopiev.spring.config.DatabaseProperties;
import org.alexprokopiev.spring.dto.CompanyReadDto;
import org.alexprokopiev.spring.integration.IntegrationTestBase;
import org.alexprokopiev.spring.integration.annotation.IT;
import org.alexprokopiev.spring.service.CompanyService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationRunner.class, initializers = ConfigDataApplicationContextInitializer.class)
public class CompanyServiceIT extends IntegrationTestBase {

    private static final Integer COMPANY_ID = 1;

    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById() {
        var actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        var expectedResult = new CompanyReadDto(COMPANY_ID, null);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}
