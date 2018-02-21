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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ServiceRowPriceExtendDto {

    private UUID id;

    private String name;

    private String description;

    private String logo;

    private Boolean freeze;

    private List<ServicePriceEntity> servicePriceEntities;

    private List<SubCategoryEntity> subCategoryEntities;

    private LocalDateTime createTime;


    public ServiceRowPriceExtendDto(UUID id, String name, String description, String logo, Boolean freeze,
                                    List<ServicePriceEntity> servicePriceEntities,
                                    List<SubCategoryEntity> subCategoryEntities,
                                    LocalDateTime createTime) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.freeze = freeze;
        this.servicePriceEntities = servicePriceEntities;
        this.subCategoryEntities = subCategoryEntities;
        this.createTime = createTime;
    }

    public UUID getId() {

        return id;
    }

    public void setId(UUID id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getLogo() {

        return logo;
    }

    public void setLogo(String logo) {

        this.logo = logo;
    }

    public Boolean getFreeze() {

        return freeze;
    }

    public void setFreeze(Boolean freeze) {

        this.freeze = freeze;
    }

    public List<ServicePriceEntity> getServicePriceEntities() {

        return servicePriceEntities;
    }

    public void setServicePriceEntities(List<ServicePriceEntity> servicePriceEntities) {

        this.servicePriceEntities = servicePriceEntities;
    }

    public LocalDateTime getCreateDate() {

        return createTime;
    }

    public void setCreateDate(LocalDateTime createDate) {

        this.createTime = createDate;
    }


    public List<SubCategoryEntity> getSubCategoryEntities() {

        return subCategoryEntities;
    }

    public void setSubCategoryEntities(
            List<SubCategoryEntity> subCategoryEntities) {

        this.subCategoryEntities = subCategoryEntities;
    }
}
