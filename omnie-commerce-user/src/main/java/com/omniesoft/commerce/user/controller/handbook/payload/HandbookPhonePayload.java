package com.omniesoft.commerce.user.controller.handbook.payload;

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
public class HandbookPhonePayload {
    private UUID id;

    @NotBlank
    @Pattern(regexp = "(^(\\+\\d{1,5})?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}$)|(\\+[\\d\\s]{7,20})$")
    @ApiModelProperty(
            required = true)
    private String phone;
}
