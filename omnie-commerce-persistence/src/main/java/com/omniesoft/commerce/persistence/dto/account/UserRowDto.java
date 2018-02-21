package com.omniesoft.commerce.persistence.dto.account;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 03.08.17
 */
public class UserRowDto {
    private UUID id;
    private String login;
    private String email;
    private LocalDateTime registrationDate;
    private String firstName;
    private String surName;

    public UserRowDto(UUID id, String login, String email, LocalDateTime registrationDate, String firstName, String surName) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.registrationDate = registrationDate;
        this.firstName = firstName;
        this.surName = surName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
}