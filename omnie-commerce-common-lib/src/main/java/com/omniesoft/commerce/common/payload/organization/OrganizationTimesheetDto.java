package com.omniesoft.commerce.common.payload.organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * @author Vitalii Martynovskyi
 * @since 04.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationTimesheetDto {

    @NotNull
    @ApiModelProperty(required = true, example = "08:00")
    private LocalTime workStart;

    @NotNull
    @ApiModelProperty(required = true, example = "18:00")
    private LocalTime workEnd;

    private DayOfWeek day;

    @ApiModelProperty(example = "13:00")
    private LocalTime breakStart;

    @ApiModelProperty(example = "14:00")
    private LocalTime breakEnd;

}
