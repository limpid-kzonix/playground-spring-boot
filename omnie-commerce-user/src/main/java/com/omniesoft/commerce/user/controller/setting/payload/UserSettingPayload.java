package com.omniesoft.commerce.user.controller.setting.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel()
public class UserSettingPayload {

    @NotNull
    @ApiModelProperty(example = "true", dataType = "boolean", required = true)
    private Boolean eventNotification;

    @NotNull
    @ApiModelProperty(example = "true", dataType = "boolean", required = true)
    private Boolean pushNotification;

    @NotNull
    @ApiModelProperty(example = "true", dataType = "boolean", required = true)
    private Boolean soundNotification;

    @NotNull
    @ApiModelProperty(example = "true", dataType = "boolean", required = true)
    private Boolean backgroundNotification;

    @Min(1)
    @NotNull
    @ApiModelProperty(notes = "in minutes", example = "15", required = true)
    private Integer notificationDelay;

    @NotNull
    @ApiModelProperty(example = "true", dataType = "boolean", required = true)
    private Boolean calendarSync;

    @NotNull
    @ApiModelProperty(example = "true", dataType = "boolean", required = true)
    private Boolean lightTheme;

}
