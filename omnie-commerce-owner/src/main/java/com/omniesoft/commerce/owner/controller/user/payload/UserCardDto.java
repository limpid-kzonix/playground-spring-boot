package com.omniesoft.commerce.owner.controller.user.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 19.10.17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCardDto {
    private UUID id;
    private String email;
    private String imageId;
    private String fullName;
}
