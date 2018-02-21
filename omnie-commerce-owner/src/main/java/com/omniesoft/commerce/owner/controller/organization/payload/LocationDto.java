package com.omniesoft.commerce.owner.controller.organization.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vitalii Martynovskyi
 * @since 09.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    @ApiModelProperty(required = true)
    private String placeId;

    @ApiModelProperty(required = true)
    private Double longitude;

    @ApiModelProperty(required = true)
    private Double latitude;
}
