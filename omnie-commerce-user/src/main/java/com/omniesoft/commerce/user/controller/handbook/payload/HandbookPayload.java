package com.omniesoft.commerce.user.controller.handbook.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class HandbookPayload {

    @ApiModelProperty(notes = "Use only for read and update", readOnly = true)
    private UUID id;
    @NotBlank
    @ApiModelProperty(required = true, notes = "Name of organization in handbook")
    private String name;
    @NotBlank
    @ApiModelProperty(required = true, notes = "Address of organization as string", example = "")
    private String address;
    @ApiModelProperty(notes = "Image-id from ImageService")
    private String image;
    @Valid
    @NotEmpty
    private List<HandbookPhonePayload> phones;
    @Valid
    @NotEmpty
    private Set<HandbookTagPayload> tags;

}
