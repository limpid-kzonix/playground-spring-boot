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

import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceRowAdminExtendDto {

    private UUID id;

    private String name;

    private String description;

    private String logo;

    private Boolean freeze;

    private String inaccessibilityReason;

    private LocalDateTime createTime;

    public ServiceRowAdminExtendDto(UUID id, String name, String description, String logo, Boolean freeze,
                                    String inaccessibilityReason, LocalDateTime createTime) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.freeze = freeze;
        this.inaccessibilityReason = inaccessibilityReason;
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

    public String getInaccessibilityReason() {

        return inaccessibilityReason;
    }

    public void setInaccessibilityReason(String inaccessibilityReason) {

        this.inaccessibilityReason = inaccessibilityReason;
    }

    public LocalDateTime getCreateDate() {

        return createTime;
    }

    public void setCreateDate(LocalDateTime createDate) {

        this.createTime = createDate;
    }
}
