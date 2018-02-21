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

package com.omniesoft.commerce.persistence.dto.service;

import com.omniesoft.commerce.persistence.entity.category.SubCategoryEntity;
import com.omniesoft.commerce.persistence.entity.service.ServicePriceEntity;

import java.util.List;
import java.util.UUID;


public class ServiceRowPriceDto {
    private UUID id;
    private String name;
    private String logo;
    private ServicePriceEntity prices;
    private List<SubCategoryEntity> subCategories;

    public ServiceRowPriceDto(UUID id, String name, String logo,
                              ServicePriceEntity prices,
                              List<SubCategoryEntity> subCategories) {

        this.id = id;
        this.name = name;
        this.logo = logo;
        this.prices = prices;

        this.subCategories = subCategories;
    }

    public List<SubCategoryEntity> getSubCategories() {

        return subCategories;
    }

    public void setSubCategories(
            List<SubCategoryEntity> subCategories) {

        this.subCategories = subCategories;
    }


    public String getLogo() {

        return logo;
    }

    public void setLogo(String logo) {

        this.logo = logo;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public UUID getId() {

        return id;
    }

    public void setId(UUID id) {

        this.id = id;
    }

    public ServicePriceEntity getPrices() {

        return prices;
    }

    public void setPrices(ServicePriceEntity prices) {

        this.prices = prices;
    }
}
