package com.omniesoft.commerce.owner.controller.service.payload;

import com.omniesoft.commerce.persistence.entity.enums.LanguageCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class LanguagePayload {
    @ApiModelProperty(required = true, notes = "Use for set language to service")
    private UUID id;
    @ApiModelProperty(notes = "Three-letter language code ISO 639-3")
    private LanguageCode name;
}
