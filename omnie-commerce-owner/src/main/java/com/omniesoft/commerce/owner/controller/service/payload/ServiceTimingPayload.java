package com.omniesoft.commerce.owner.controller.service.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTimingPayload {
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean minDuration;

    @NotNull
    @Min(60000)
    @ApiModelProperty(required = true)
    private Long durationMillis;

    @NotNull
    @Max(86400000)
    @ApiModelProperty(required = true)
    private Long pauseMillis;

    @Min(1)
    @NotNull
    @ApiModelProperty(required = true)
    private Integer slotCount;

    @NotNull
    @DecimalMax("100.0")
    @DecimalMin("0.0")
    @ApiModelProperty(required = true)
    private Double maxDiscount;


    private LocalDateTime activeFrom;

    private LocalDateTime createTime;
}
