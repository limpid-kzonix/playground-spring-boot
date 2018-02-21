package com.omniesoft.commerce.common.converter.impl;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.omniesoft.commerce.common.converter.OrganizationTimeSheetConverter;
import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationTimeSheetEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrganizationTimeSheetConverterImpl implements OrganizationTimeSheetConverter {

    @Override
    public OrganizationTimesheetDto toTimeSheetDto(OrganizationTimeSheetEntity organizationTimeSheetEntity) {
        if (organizationTimeSheetEntity == null) {
            throw new UsefulException();
        }
        return new OrganizationTimesheetDto(
                organizationTimeSheetEntity.getWorkStart(),
                organizationTimeSheetEntity.getWorkEnd(),
                organizationTimeSheetEntity.getDay(),
                organizationTimeSheetEntity.getBreakStart(),
                organizationTimeSheetEntity.getBreakEnd()
        );
    }

    @Override
    public Map<DayOfWeek, List<OrganizationTimesheetDto>> toTimeSheet(List<OrganizationTimeSheetEntity> organizationTimeSheetEntities) {
        ImmutableListMultimap<DayOfWeek, OrganizationTimeSheetEntity> index = index(organizationTimeSheetEntities);
        ListMultimap<DayOfWeek, OrganizationTimesheetDto> dayOfWeekOrganizationTimesheetDtoListMultimap = transformValues(index);
        return Multimaps.asMap
                (dayOfWeekOrganizationTimesheetDtoListMultimap);
    }

    private ListMultimap<DayOfWeek, OrganizationTimesheetDto> transformValues(
            ImmutableListMultimap<DayOfWeek, OrganizationTimeSheetEntity> index) {
        return Multimaps
                .transformValues(index, this::toTimeSheetDto);
    }

    private ImmutableListMultimap<DayOfWeek, OrganizationTimeSheetEntity> index(List<OrganizationTimeSheetEntity> organizationTimeSheetEntities) {
        return Multimaps.index(
                organizationTimeSheetEntities,
                OrganizationTimeSheetEntity::getDay
        );
    }
}
