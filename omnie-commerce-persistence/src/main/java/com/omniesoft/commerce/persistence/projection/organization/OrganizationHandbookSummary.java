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

package com.omniesoft.commerce.persistence.projection.organization;

import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookPhoneSummary;
import com.omniesoft.commerce.persistence.projection.service.ServiceHbSummary;

import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 11.10.17
 */
public interface OrganizationHandbookSummary {
    UUID getId();

    String getName();

    String getLogoId();

    String getPlaceId();

    Double getLongitude();

    Double getLatitude();

    List<ServiceHbSummary> getServices();

    List<HandbookPhoneSummary> getPhones();

}
