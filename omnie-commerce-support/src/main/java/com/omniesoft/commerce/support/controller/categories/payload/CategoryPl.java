package com.omniesoft.commerce.support.controller.categories.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CategoryPl {

    private UUID id;

    private String logoId;

    private String engName;

    private String rusName;

    private String ukrName;

    private LocalDateTime createTime;

    private int servicesCount;

    private int organizationCount;
}
