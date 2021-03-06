/*
 * Copyright (c) 2017.
 * All rights reserved. No part of this publication may be reproduced,
 * distributed, or transmitted in any form or by any means, including photocopying,
 * recording, or other electronic or mechanical methods, without the prior written
 * permission of the publisher, except in the case of brief quotations embodied in
 * critical reviews and certain other noncommercial uses permitted by copyright law.
 * For permission requests, write to the publisher, addressed
 * “Attention: Permissions Coordinator,” at the address below.
 */

package com.omniesoft.commerce.owner.controller.user.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPayload {

    private String email;

    private String firstName;

    private String lastName;

    private String image;

    private Long countOfOrder;

    private Double sumOfOrdersPrice;

    private LocalDateTime lastOrderDateTime;

    private List<CustomerGroupPayload> customerGroups;
}
