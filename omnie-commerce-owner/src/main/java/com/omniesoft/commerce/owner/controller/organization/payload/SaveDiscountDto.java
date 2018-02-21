package com.omniesoft.commerce.owner.controller.organization.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Vitalii Martynovskyi
 * @since 24.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveDiscountDto {

    @NotBlank
    private String name;

    @ApiModelProperty(required = true, example = "2017-10-30T08:00")
    @NotNull(message = "startTime must be specified")
    private LocalDateTime startTime;

    @ApiModelProperty(required = true, example = "2017-10-30T23:00")
    @NotNull(message = "endTime must be specified")
    private LocalDateTime endTime;

    @ApiModelProperty(required = true)
    @Min(value = 0)
    @Max(value = 100)
    @NotNull(message = "endTime must be specified")
    private Double percent;

    @ApiModelProperty(required = true)
    @NotNull(message = "personalStatus must be specified")
    private Boolean personalStatus;


}