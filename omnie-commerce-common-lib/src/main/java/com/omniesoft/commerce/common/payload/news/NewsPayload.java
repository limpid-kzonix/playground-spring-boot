/*
 * Copyright (c)  2017
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.common.payload.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        value = "News payload",
        description = "Use for creation news and retrieving news content"
)
public class NewsPayload {

    @ApiModelProperty(readOnly = true)
    private UUID id;

    @ApiModelProperty(required = true)
    private String imageId;

    @NotBlank
    @ApiModelProperty(required = true)
    private String title;

    @NotBlank
    @ApiModelProperty(required = true)
    private String text;

    @NotNull
    private Boolean promotionStatus;

    @ApiModelProperty(required = false, allowEmptyValue = true)
    private List<UUID> services;

    @NotNull
    @ApiModelProperty(required = true, example = "2017-10-30T08:00", notes = "DateTimeFormat = ISO8601DateFormat( yyyy-MM-dd HH:mm:ss,SSS)")
    private LocalDateTime postTime;

}
