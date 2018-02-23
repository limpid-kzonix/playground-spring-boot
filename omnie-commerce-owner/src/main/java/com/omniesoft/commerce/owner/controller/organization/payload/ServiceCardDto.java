package com.omniesoft.commerce.owner.controller.organization.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 04.10.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCardDto {
    private UUID id;
    private String name;
    private String email;
    private String title;
    private String logoId;
}
