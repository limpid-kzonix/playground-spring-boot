package com.omniesoft.commerce.user.controller.temp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class UpdateHandbookPl {
    @NotBlank
    @ApiModelProperty(required = true, notes = "Name of organization in handbook")
    private String name;
    @NotBlank
    @ApiModelProperty(required = true, notes = "Address of organization as string")
    private String address;
    @ApiModelProperty(notes = "Image-id from ImageService")
    private String imageId;
}
