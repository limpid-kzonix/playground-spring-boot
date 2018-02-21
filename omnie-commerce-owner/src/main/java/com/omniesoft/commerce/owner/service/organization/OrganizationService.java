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

package com.omniesoft.commerce.owner.service.organization;

import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.owner.controller.organization.payload.*;
import com.omniesoft.commerce.persistence.entity.account.OwnerEntity;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationCardSummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationGallerySummary;
import com.omniesoft.commerce.persistence.projection.organization.OrganizationPhoneSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 27.09.17
 */
public interface OrganizationService {
    CardOrganizationDto save(SaveOrganizationDto organization, UserEntity user);

    OrganizationEntity findById(UUID organizationId);

    OrganizationDto findByIdFullProfile(UUID organizationId);

    UUID addToGallery(String imageId, UUID organizationId);

    void deleteFromGallery(UUID deleteImages, UUID organizationId);

    void freezeOrganization(UUID organizationId, String reason);

    void deleteOrganization(UUID organizationId, String reason);

    void unfreezeOrganization(UUID organizationId);

    void setBackgroundImage(UUID organizationId, String imageId);

    void deleteBackgroundImage(UUID organizationId);

    void setLogoImage(UUID organizationId, String imageId);

    void deleteLogoImage(UUID organizationId);

    void setLocation(UUID organizationId, LocationDto location);

    void deletePhone(UUID organizationId, UUID phoneId);

    UUID addPhone(UUID organizationId, String phone);

    void updatePhone(UUID phoneId, UUID organizationId, String phone);

    List<OrganizationTimesheetDto> saveTimesheet(UUID organizationId, DayOfWeek day, SaveTimesheetListDto timesheetDto);

    void updateTimesheet(UUID organizationId, DayOfWeek day, SaveTimesheetListDto timesheetDto);

    void deleteTimesheet(UUID organizationId, DayOfWeek day);

    void updateDescription(UUID organizationId, OrganizationDescriptionDto description);

    List<OrganizationCardSummary> findByOwner(OwnerEntity ownerAccount);

    List<OrganizationGallerySummary> getGallery(UUID organizationId);

    List<OrganizationPhoneSummary> getPhones(UUID organizationId);

    Map<DayOfWeek, List<OrganizationTimesheetDto>> getTimesheet(UUID organizationId);

    Page<OrganizationCardSummary> findByOwnerOrAdmin(UserEntity user, Pageable pageable);
}
