package com.omniesoft.commerce.user.controller.handbook.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandbookTagPayload {
    private UUID id;
    @NotBlank
    private String name;
}
