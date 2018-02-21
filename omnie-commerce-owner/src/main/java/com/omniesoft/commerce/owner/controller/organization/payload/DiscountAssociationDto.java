package com.omniesoft.commerce.owner.controller.organization.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 26.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountAssociationDto {
    private UUID id;
    private String name;
}
