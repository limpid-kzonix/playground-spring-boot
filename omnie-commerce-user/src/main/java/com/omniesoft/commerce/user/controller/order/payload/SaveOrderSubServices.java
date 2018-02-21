package com.omniesoft.commerce.user.controller.order.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SaveOrderSubServices {

    @ApiModelProperty(required = true)
    @NotNull
    @Min(1)
    private int count;


    @NotNull
    @Min(0)
    private Long duration = 0L;

    @ApiModelProperty(required = true, dataType = "UUID")
    @NotNull
    private UUID subServiceId;

    @ApiModelProperty(readOnly = true)
    private Double discountPercent;

    @ApiModelProperty(readOnly = true, dataType = "UUID")
    private UUID discountId;

}
