package com.omniesoft.commerce.owner.controller.organization.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 04.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto {
    @ApiModelProperty(required = true)
    private UUID id;
    @ApiModelProperty(required = true)
    private String name;
    private String email;
    private String title;
    private String description;
    private String logoId;
    private String backgroundImageId;
    @ApiModelProperty(required = true)
    private String placeId;
    private Double longitude;
    private Double latitude;
    private Boolean freezeStatus;
    private Boolean deleteStatus;
    private String reason;
    private List<GalleryDto> gallery;
    private List<PhoneDto> phones;
    private List<FullTimesheetDto> timesheet;
    private List<ServiceCardDto> services;
}

