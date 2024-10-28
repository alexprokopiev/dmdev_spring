package org.alexprokopiev.spring.dto;

import lombok.Value;
import org.alexprokopiev.spring.database.entity.Role;

import java.time.LocalDate;

@Value
public class UserReadDto {

    Long id;
    String username;
    LocalDate birthDate;
    String firstname;
    String lastname;
    String image;
    Role role;
    CompanyReadDto company;
}
