package com.omniesoft.commerce.owner.controller.organization.payload;

import com.omniesoft.commerce.common.payload.organization.OrganizationTimesheetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 04.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveTimesheetListDto {

    @NotEmpty
    private List<OrganizationTimesheetDto> configForDay;

}
