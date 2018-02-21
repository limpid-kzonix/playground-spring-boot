package com.omniesoft.commerce.owner.controller.conversation.payload;

import com.omniesoft.commerce.common.payload.account.AccountDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

@Data
@ApiModel(value = "Message")
@AllArgsConstructor
@NoArgsConstructor
public class MessagePayload {
    @ApiModelProperty(readOnly = true, notes = "Use only as read-only value")
    private UUID id;

    @ApiModelProperty(readOnly = true, notes = "Use only as read-only value")
    private Boolean readUserStatus;

    @ApiModelProperty(readOnly = true, notes = "Use only as read-only value")
    private Boolean readOrgStatus;

    @NotBlank
    private String message;

    @ApiModelProperty(readOnly = true)
    private AccountDto sender;
}
