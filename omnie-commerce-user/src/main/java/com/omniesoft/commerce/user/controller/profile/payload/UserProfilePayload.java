package com.omniesoft.commerce.user.controller.profile.payload;

import com.omniesoft.commerce.persistence.entity.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfilePayload {

    @NotBlank
    @ApiModelProperty(
            required = true
    )
    private String firstName;
    @NotBlank
    @ApiModelProperty(
            required = true
    )
    private String lastName;

    @NotNull
    @ApiModelProperty(
            required = true
    )
    private LocalDate birthday;

    @ApiModelProperty(
            required = false
    )
    private String imageId;

    @NotBlank
    @Pattern(regexp = "(^(\\+\\d{1,5})?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}$)|(\\+[\\d\\s]{7,20})$")
    @ApiModelProperty(
            required = true
    )
    private String phone;

    @ApiModelProperty(
            required = true,
            allowableValues = "MALE,FEMALE"
    )
    private Gender gender;

}
