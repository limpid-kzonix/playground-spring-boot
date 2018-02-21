/*
 * Copyright (c)  2017
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.persistence.dto.organization;


import com.omniesoft.commerce.persistence.entity.organization.CustomerGroupEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CustomerDto {

    private UUID id;

    private String login;

    private String email;

    private String firstName;

    private String surName;

    private String image;

    private Long countOfOrder;

    private Double sumOfOrdersPrice;

    private LocalDateTime lastOrderDateTime;

    private List<CustomerGroupEntity> groupEntities;

    public CustomerDto(UUID id, String login, String email, String firstName, String surName, String image,
                       Long countOfOrder, Double sumOfOrdersPrice, LocalDateTime lastOrderDateTime,
                       List<CustomerGroupEntity> groupEntities) {

        this.id = id;
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.image = image;
        this.countOfOrder = countOfOrder;
        this.sumOfOrdersPrice = sumOfOrdersPrice;
        this.lastOrderDateTime = lastOrderDateTime;
        this.groupEntities = groupEntities;
    }

    public CustomerDto(UUID id, String login, String email,
                       String firstName, String surName, String image,
                       Long countOfOrder, Double sumOfOrdersPrice, LocalDateTime lastOrderDateTime) {

        this.id = id;
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.image = image;
        this.countOfOrder = countOfOrder;
        this.sumOfOrdersPrice = sumOfOrdersPrice;
        this.lastOrderDateTime = lastOrderDateTime;
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

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public Long getCountOfOrder() {

        return countOfOrder;
    }

    public void setCountOfOrder(Long countOfOrder) {

        this.countOfOrder = countOfOrder;
    }

    public Double getSumOfOrdersPrice() {

        return sumOfOrdersPrice;
    }

    public void setSumOfOrdersPrice(Double sumOfOrdersPrice) {

        this.sumOfOrdersPrice = sumOfOrdersPrice;
    }

    public LocalDateTime getLastOrderDateTime() {

        return lastOrderDateTime;
    }

    public void setLastOrderDateTime(LocalDateTime lastOrderDateTime) {

        this.lastOrderDateTime = lastOrderDateTime;
    }

    public List<CustomerGroupEntity> getGroupEntities() {

        return groupEntities;
    }

    public void setGroupEntities(
            List<CustomerGroupEntity> groupEntities) {

        this.groupEntities = groupEntities;
    }
}
