package com.omniesoft.commerce.owner.service.organization.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import com.omniesoft.commerce.owner.service.organization.OrganizationTimeSheetValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationTimeSheetValidatorImpl implements OrganizationTimeSheetValidator {
    @Override
    public void validate(List<OrganizationTimesheetDto> organizationTimesheetDtos) {
        if (organizationTimesheetDtos.size() > 2) {
            validateByBreakHours(organizationTimesheetDtos);
            validateByWorkHours(organizationTimesheetDtos);
        }
    }

    private void validateByBreakHours(List<OrganizationTimesheetDto> organizationTimesheetDtos) {
        organizationTimesheetDtos.sort(Comparator.comparing(OrganizationTimesheetDto::getWorkStart));
        for (int i = 0; i < organizationTimesheetDtos.size() - 1; i++) {
            if (!compareByBreakHours(organizationTimesheetDtos, i))
                throw new UsefulException(OwnerModuleErrorCodes.ORGANIZATION_TIMESHEET_VALIDATING_ERROR_BY_BREAK_HOURS);
        }
    }

    private void validateByWorkHours(List<OrganizationTimesheetDto> organizationTimesheetDtos) {
        organizationTimesheetDtos.sort(Comparator.comparing(OrganizationTimesheetDto::getBreakStart));
        for (int i = 0; i < organizationTimesheetDtos.size() - 1; i++) {
            if (!compareByWorkHours(organizationTimesheetDtos, i))
                throw new UsefulException(OwnerModuleErrorCodes.ORGANIZATION_TIMESHEET_VALIDATING_ERROR_BY_WORK_HOURS);
        }
    }

    private boolean compareByBreakHours(List<OrganizationTimesheetDto> organizationTimesheetDtos, int i) {

        return intersectByBreakHours(organizationTimesheetDtos, i);
    }

    private boolean compareByWorkHours(List<OrganizationTimesheetDto> organizationTimesheetDtos, int i) {

        return intersectByWorkHours(organizationTimesheetDtos, i);
    }


    private boolean intersectByBreakHours(List<OrganizationTimesheetDto> organizationTimesheetDtos, int i) {
        return intersectByBreakHours(organizationTimesheetDtos.get(i), organizationTimesheetDtos.get(i + 1));
    }

    private boolean intersectByWorkHours(List<OrganizationTimesheetDto> organizationTimesheetDtos, int i) {
        return intersectByWorkHours(organizationTimesheetDtos.get(i), organizationTimesheetDtos.get(i + 1));
    }

    private boolean intersectByWorkHours(OrganizationTimesheetDto p1, OrganizationTimesheetDto p2) {

        return p1.getWorkStart().isBefore(p1.getWorkEnd())
                && ((p2.getWorkStart().isAfter(p1.getWorkEnd()) || p2.getWorkStart().equals(p1.getWorkEnd()))

        );
    }

    private boolean intersectByBreakHours(OrganizationTimesheetDto p1, OrganizationTimesheetDto p2) {

        return p1.getWorkStart().isBefore(p1.getWorkEnd())
                && ((p2.getWorkStart().isAfter(p1.getWorkEnd()) || p2.getWorkStart().equals(p1.getWorkEnd()))

        );
    }
}
