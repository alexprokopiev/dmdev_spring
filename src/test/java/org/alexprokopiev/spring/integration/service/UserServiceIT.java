package org.alexprokopiev.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.alexprokopiev.spring.database.entity.Role;
import org.alexprokopiev.spring.dto.UserCreateEditDto;
import org.alexprokopiev.spring.dto.UserReadDto;
import org.alexprokopiev.spring.integration.IntegrationTestBase;
import org.alexprokopiev.spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    private static final Integer COMPANY_1 = 1;

    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        Optional<UserReadDto> user = userService.findById(USER_1);
        assertTrue(user.isPresent());
        user.ifPresent(u -> assertEquals("ivan@gmail.com", u.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto("test@gmail.com",
                                                                    "test",
                                                                    LocalDate.now(),
                                                            "Test",
                                                            "Test",
                                                                    Role.ADMIN,
                                                                    COMPANY_1,
                                                                    new MockMultipartFile("test", new byte[0]));
        UserReadDto actual = userService.create(userDto);
        assertEquals(userDto.getUsername(), actual.getUsername());
        assertEquals(userDto.getBirthDate(), actual.getBirthDate());
        assertEquals(userDto.getFirstname(), actual.getFirstname());
        assertEquals(userDto.getLastname(), actual.getLastname());
        assertEquals(userDto.getCompanyId(), actual.getCompany().id());
        assertSame(userDto.getRole(), actual.getRole());
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto("test@gmail.com",
                                                        "test",
                                                                    LocalDate.now(),
                                                            "Test",
                                                            "Test",
                                                                    Role.ADMIN,
                                                                    COMPANY_1,
                                                                    new MockMultipartFile("test", new byte[0]));
        Optional<UserReadDto> actual = userService.update(1L, userDto);
        assertTrue(actual.isPresent());
        actual.ifPresent(user -> {
            assertEquals(userDto.getUsername(), user.getUsername());
            assertEquals(userDto.getBirthDate(), user.getBirthDate());
            assertEquals(userDto.getFirstname(), user.getFirstname());
            assertEquals(userDto.getLastname(), user.getLastname());
            assertEquals(userDto.getCompanyId(), user.getCompany().id());
            assertSame(userDto.getRole(), user.getRole());
        });
    }

    @Test
    void delete() {
        assertTrue(userService.delete(USER_1));
        assertFalse(userService.delete(-42L));
    }
}
