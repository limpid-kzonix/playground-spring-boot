package com.omniesoft.commerce.common.component.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 21.11.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveFullOrderSubServices {

    @ApiModelProperty(required = true)
    @NotNull
    @Min(1)
    private int count;

    @Min(0)
    private Long duration = 0L;

    @ApiModelProperty(required = true, dataType = "UUID")
    @NotNull
    private UUID subServiceId;

    @ApiModelProperty(required = true)
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double discountPercent;

    @ApiModelProperty(dataType = "UUID")
    private UUID discountId;

}
