package com.omniesoft.commerce.common.converter;

import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationTimeSheetEntity;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface OrganizationTimeSheetConverter {
    OrganizationTimesheetDto toTimeSheetDto(OrganizationTimeSheetEntity organizationTimeSheetEntity);

    Map<DayOfWeek, List<OrganizationTimesheetDto>> toTimeSheet(List<OrganizationTimeSheetEntity> servicePriceEntities);

}
