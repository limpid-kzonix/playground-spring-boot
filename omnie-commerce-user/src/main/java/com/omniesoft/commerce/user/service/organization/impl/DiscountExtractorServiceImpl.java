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

package com.omniesoft.commerce.user.service.organization.impl;

import com.omniesoft.commerce.common.payload.discount.DiscountPayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.user.service.organization.DiscountExtractorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiscountExtractorServiceImpl implements DiscountExtractorService {

    private final ModelMapper modelMapper;

    @Override
    public List<DiscountPayload> extractUsersDiscount(List<DiscountEntity> discountEntities, UserEntity userEntity) {

        return discountEntities.stream().filter(discountEntity -> discountEntity.getAssociatedUsers().contains(
                userEntity)).map(this::mapToPayload).collect(Collectors.toList());
    }


    private DiscountPayload mapToPayload(DiscountEntity discountEntity) {
        return modelMapper.map(discountEntity, DiscountPayload.class);
    }

    @Override
    public List<DiscountPayload> extractDiscount(List<DiscountEntity> discountEntities) {

        return discountEntities.stream().map(this::mapToPayload).collect(Collectors.toList());
    }
}
