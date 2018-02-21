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

package com.omniesoft.commerce.persistence.repository.organization.impl;

import com.omniesoft.commerce.persistence.repository.organization.OrganizationReviewRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public class OrganizationReviewRepositoryImpl implements OrganizationReviewRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void confirmReview(UUID reviewId) {
        em.createQuery("update OrganizationReviewEntity ore " +
                "set ore.acceptStatus = true " +
                "where ore.id = :reviewId")
                .setParameter("reviewId", reviewId).executeUpdate();
    }
}
