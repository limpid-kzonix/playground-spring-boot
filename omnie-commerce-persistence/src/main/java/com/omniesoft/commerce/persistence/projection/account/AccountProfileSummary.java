package com.omniesoft.commerce.persistence.projection.account;

import com.omniesoft.commerce.persistence.entity.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public interface AccountProfileSummary {

    UUID getId();

    String getFirstName();

    String getLastName();

    LocalDate getBirthday();

    String getImageId();

    String getPhone();

    Gender getGender();

    String getOmnieCard();
}
