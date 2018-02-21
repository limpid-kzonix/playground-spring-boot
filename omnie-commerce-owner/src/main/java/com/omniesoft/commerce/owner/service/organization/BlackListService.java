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

package com.omniesoft.commerce.owner.service.organization;

import com.omniesoft.commerce.owner.controller.organization.payload.blacklist.BlackListPayload;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.organization.BlackListSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BlackListService {

    BlackListPayload addToBlackList(String comment, UUID userId, UUID orgId, UserEntity userEntity);

    void deleteFromBlackList(UUID userId, UUID orgId, UserEntity userEntity);

    Page<BlackListSummary> getUsersInBlackList(UUID orgId, String searchPattern, UserEntity userEntity, Pageable pageable);


}
