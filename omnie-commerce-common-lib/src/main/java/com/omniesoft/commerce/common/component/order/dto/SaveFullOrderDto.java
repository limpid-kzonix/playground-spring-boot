package com.omniesoft.commerce.common.component.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 21.11.17
 */
@Data
public class SaveFullOrderDto {
    @ApiModelProperty(required = true, example = "2017-12-01T10:00")
    @NotNull
    private LocalDateTime start;

    @ApiModelProperty(required = true, example = "2017-12-01T11:00")
    @NotNull
    private LocalDateTime end;

    @ApiModelProperty(required = true, hidden = true, dataType = "java.util.UUID")
    private UUID serviceId;

    @ApiModelProperty(dataType = "java.util.UUID")
    private UUID customerId;

    private String customerName;

    private String customerPhone;

    private String comment;

    @Valid
    private List<SaveFullOrderSubServices> subServices;

    @ApiModelProperty(required = true, example = "0.0")
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double discountPercent;

    @ApiModelProperty(dataType = "UUID")
    private UUID discountId;
}

