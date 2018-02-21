package com.omniesoft.commerce.security.social.service.oauth.model;

import com.omniesoft.commerce.persistence.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialProfile {

    private String id;

    private String name;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private LocalDate birthDate;

    private Gender gender;
}
