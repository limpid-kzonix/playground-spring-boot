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

package com.omniesoft.commerce.common.payload.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

@Data
@ApiModel("Sub-service model")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class SubServiceShortPayload {

    @ApiModelProperty(
            name = "Id of sub-service",
            notes = "Use only for read",
            readOnly = true
    )
    @NonNull
    private UUID id;

    @ApiModelProperty(
            required = true,
            name = "name of sub-service"
    )
    @NotBlank
    private String name;
}
