package com.omniesoft.commerce.common.payload.service;

import com.omniesoft.commerce.common.order.schedule.ScheduleSetting;
import com.omniesoft.commerce.persistence.entity.enums.PriceUnit;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePricePayload implements ScheduleSetting {

    @NotNull
    @ApiModelProperty(required = true, example = "10:00", dataType = "java.time.LocalTime")
    private LocalTime start;

    @NotNull
    @ApiModelProperty(required = true, example = "18:00", dataType = "java.time.LocalTime")
    private LocalTime end;


    @ApiModelProperty(readOnly = true, notes = "Use only as property for read operation")
    private DayOfWeek day;

    @DecimalMin("0.0")
    @NotNull
    @ApiModelProperty(required = true, dataType = "double")
    private Double price;

    @NotNull
    @ApiModelProperty(required = true, dataType = "PriceUnit")
    private PriceUnit priceUnit;

    @NotNull
    @ApiModelProperty(required = true, dataType = "double")
    private Double expense;

    @ApiModelProperty(readOnly = true, example = "2017-10-30T23:00", dataType = "java.time.LocalDateTime")
    private LocalDateTime activeFrom;

    @ApiModelProperty(readOnly = true, example = "2017-10-30T23:00", dataType = "java.time.LocalDateTime")
    private LocalDateTime createTime;
}
