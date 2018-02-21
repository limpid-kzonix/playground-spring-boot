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

package com.omniesoft.commerce.owner.service.service.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.OwnerModuleErrorCodes;
import com.omniesoft.commerce.common.payload.service.ServicePricePayload;
import com.omniesoft.commerce.owner.service.service.ServicePriceTimeSettingValidator;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
public class ServicePriceTimeSettingValidatorImpl implements ServicePriceTimeSettingValidator {


    @Override
    public void validate(List<ServicePricePayload> pricePayloads) {

        if (pricePayloads.size() > 2) {
            pricePayloads.sort(Comparator.comparing(ServicePricePayload::getStart));
            for (int i = 0; i < pricePayloads.size() - 1; i++) {
                if (!compare(pricePayloads, i))
                    throw new UsefulException(OwnerModuleErrorCodes.SERVICE_TIMESHEET_VALIDATING_ERROR);
            }
        }


    }

    private boolean compare(List<ServicePricePayload> pricePayloads, int i) {

        return intersect(pricePayloads.get(i), pricePayloads.get(i + 1));
    }

    private boolean intersect(ServicePricePayload p1, ServicePricePayload p2) {

        return p1.getStart().isBefore(p1.getEnd())
                && ((p2.getStart().isAfter(p1.getEnd()) || p2.getStart().equals(p1.getEnd()))

        );
    }
}
