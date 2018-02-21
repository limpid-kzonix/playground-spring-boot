package com.omniesoft.commerce.user.controller.card.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCardPayload {
    @ApiModelProperty(
            notes = "Use for update discount card data"
    )
    private UUID id;

    @ApiModelProperty(allowEmptyValue = true)
    private String image;

    @NotBlank
    @ApiModelProperty(
            required = true)
    private String name;

    @NotBlank
    @ApiModelProperty(
            required = true)
    private String code;

    @NotBlank
    @ApiModelProperty(
            required = true)
    private String format;

}
