package com.omniesoft.commerce.owner.controller.organization.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vitalii Martynovskyi
 * @since 10.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDescriptionDto {
    private String title;
    private String description;
}
