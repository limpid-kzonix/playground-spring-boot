package com.omniesoft.commerce.owner.controller.organization.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 04.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardOrganizationDto {
    private UUID id;
    @ApiModelProperty(required = true)
    private String name;
    private String logoId;
    @ApiModelProperty(required = true)
    private String placeId;
    private Double longitude;
    private Double latitude;
    @ApiModelProperty(required = true)
    private Boolean freezeStatus;
    private String reason;

}