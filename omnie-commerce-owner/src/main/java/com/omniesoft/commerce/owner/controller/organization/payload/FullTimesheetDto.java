package com.omniesoft.commerce.owner.controller.organization.payload;

import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 10.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FullTimesheetDto extends OrganizationTimesheetDto {
    private UUID id;
}
