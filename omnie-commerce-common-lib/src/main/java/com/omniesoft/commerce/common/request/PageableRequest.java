package com.omniesoft.commerce.common.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableRequest {
    @NotNull
    @ApiModelProperty(required = true)
    private int page;
    @NotNull
    @ApiModelProperty(required = true)
    @Max(100)
    private int size;

    @ApiModelProperty(
            name = "For example : 'name, asc' or just 'name', where name is field of model",
            example = "For example : 'name, asc' or just 'name', where name is field of model",
            notes = "For example : 'name, asc' or just 'name', where name is field of model"
    )
    private List<String> sort;
}
