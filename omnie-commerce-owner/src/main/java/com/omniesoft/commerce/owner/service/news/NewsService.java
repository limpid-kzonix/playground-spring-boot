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

package com.omniesoft.commerce.owner.service.news;

import com.omniesoft.commerce.common.payload.news.NewsPayload;
import com.omniesoft.commerce.common.payload.news.NewsWithServicePayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.NewsSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NewsService {

    UUID createNews(UUID organizationId, NewsPayload newsPayload, UserEntity userEntity);

    NewsWithServicePayload fetchNewsById(UUID organizationId, UUID newsId, UserEntity userEntity);

    void update(UUID newsId, UUID organizationId, NewsPayload newsPayload, UserEntity userEntity);

    Page<NewsSummary> fetchOrganizationNews(UUID organizationId, UserEntity userEntity, Pageable pageable);

    Page<NewsSummary> fetchOrganizationPromotions(UUID organizationId, UserEntity userEntity, Pageable pageable);


}
