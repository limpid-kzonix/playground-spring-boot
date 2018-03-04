package com.omniesoft.commerce.owner.controller.organization.payload;

import com.omniesoft.commerce.common.util.Patterns;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Vitalii Martynovskyi
 * @since 28.09.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveOrganizationDto {
    @NotNull
    @ApiModelProperty(required = true)
    private String name;
    @Pattern(regexp = Patterns.PHONE)
    @ApiModelProperty(required = true)
    private String phone;
    private String logoId;
    @Pattern(regexp = Patterns.EMAIL)
    @ApiModelProperty(required = true)
    private String email;
    @NotNull
    @ApiModelProperty(value = "PlaceId from Google API", required = true)
    private String placeId;
    @NotNull
    @ApiModelProperty(required = true)
    private Double longitude;
    @NotNull
    @ApiModelProperty(required = true)
    private Double latitude;

}
