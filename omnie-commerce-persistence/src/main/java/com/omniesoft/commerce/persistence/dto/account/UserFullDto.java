package com.omniesoft.commerce.persistence.dto.account;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 03.08.17
 */
public class UserFullDto {
    private UUID id;
    private String login;
    private String email;
    private Boolean status;
    private LocalDateTime registrationDate;
    private String firstName;
    private String surName;
    private LocalDateTime birthday;
    private String phone;
    private String image;
    private Integer gender;

    public UserFullDto(UUID id, String login, String email, Boolean status, LocalDateTime registrationDate, String firstName, String surName, LocalDateTime birthday, String phone, String image, Integer gender) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.status = status;
        this.registrationDate = registrationDate;
        this.firstName = firstName;
        this.surName = surName;
        this.birthday = birthday;
        this.phone = phone;
        this.image = image;
        this.gender = gender;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}