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

import com.omniesoft.commerce.persistence.projection.account.AccountProfileDataSummary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CustomerGroupSummary {

    UUID getId();

    String getName();

    AccountProfileDataSummary getCreateby();

    LocalDateTime getCreateTime();

    List<AccountProfileDataSummary> getUsers();


}
