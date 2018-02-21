package com.omniesoft.commerce.common.payload.service;

import com.omniesoft.commerce.persistence.entity.enums.MeasurementUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ApiModel("Sub-service model")
@NoArgsConstructor
@AllArgsConstructor
public class SubServicePayload {
    @ApiModelProperty(
            name = "Id of sub-service",
            notes = "Use only for read",
            readOnly = true
    )
    private UUID id;
    @ApiModelProperty(
            required = true,
            name = "name of sub-service"
    )
    @NotBlank
    private String name;

    @ApiModelProperty(
            required = true,
            name = "Date of activation rules",
            readOnly = true,
            example = "2017-10-30T23:00",
            dataType = "java.time.LocalDateTime"
    )
    private LocalDateTime activeFrom;

    @NotNull
    @ApiModelProperty(
            required = true,
            name = "measurement unit"
    )
    private MeasurementUnit measurementUnit;

    @ApiModelProperty(
            required = true,
            name = "duration of sub-service in millis"
    )
    private Long durationMillis;

    @ApiModelProperty(
            required = true,
            name = "expense of sub-service in millis"
    )
    private Double expense;

    @ApiModelProperty(
            required = true,
            name = "price of sub-service"
    )
    private Double price;

    @ApiModelProperty(
            required = true,
            name = "min count of sub-service"
    )
    private Integer minCount;

    @ApiModelProperty(
            required = true,
            name = "min count of sub-service"
    )
    private Integer maxCount;

    @ApiModelProperty(
            required = true,
            name = "Max discount of sub-service"
    )
    private Double maxDiscount;


}
