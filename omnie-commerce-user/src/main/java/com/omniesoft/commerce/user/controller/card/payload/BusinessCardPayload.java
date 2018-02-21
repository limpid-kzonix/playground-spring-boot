package com.omniesoft.commerce.user.controller.card.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Business card", description = "Business card")
public class BusinessCardPayload {

    @ApiModelProperty(
            notes = "Use for update business card data"
    )
    private UUID id;

    @ApiModelProperty(allowEmptyValue = true)
    private String image;

    @NotBlank
    @ApiModelProperty(
            required = true)
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$")
    @ApiModelProperty(
            required = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "(^(\\+\\d{1,5})?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}$)|(\\+[\\d\\s]{7,20})$")
    @ApiModelProperty(
            required = true)
    private String phone;

    @ApiModelProperty(allowEmptyValue = true)
    private String comment;
}
