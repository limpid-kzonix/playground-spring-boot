package com.omniesoft.commerce.owner.controller.service.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class ServicePayload {
    @ApiModelProperty(readOnly = true, notes = "Read only")
    private UUID id;
    @NotBlank
    @Size(min = 5, max = 200)
    private String name;
    private String logoId;
    @NotBlank
    private String description;

}
