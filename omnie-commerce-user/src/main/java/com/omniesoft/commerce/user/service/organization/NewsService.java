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

package com.omniesoft.commerce.user.service.organization;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.NewsExtendedSummary;
import com.omniesoft.commerce.persistence.projection.organization.NewsSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NewsService {

    Page<NewsSummary> fetchOrganizationNews(UserEntity userEntity, Pageable pageable);

    Page<NewsSummary> fetchOrganizationPromotions(UserEntity userEntity, Pageable pageable);

    NewsExtendedSummary fetchOrganizationNewById(UUID newsId, UserEntity userEntity);

    NewsExtendedSummary fetchOrganizationPromotionById(UUID promotionsId, UserEntity userEntity);

}
