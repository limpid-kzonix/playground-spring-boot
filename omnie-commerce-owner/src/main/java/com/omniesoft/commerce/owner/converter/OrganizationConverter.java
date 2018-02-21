package com.omniesoft.commerce.owner.converter;

import com.omniesoft.commerce.owner.controller.organization.payload.CardOrganizationDto;
import com.omniesoft.commerce.owner.controller.organization.payload.GalleryDto;
import com.omniesoft.commerce.owner.controller.organization.payload.OrganizationDto;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationGalleryEntity;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 04.10.17
 */
public interface OrganizationConverter {
    @Nullable
    CardOrganizationDto cardDto(OrganizationEntity organization);

    OrganizationDto organizationDto(OrganizationEntity organization);

    List<GalleryDto> gallery(Iterable<OrganizationGalleryEntity> galleryEntities);

    List<CardOrganizationDto> organizationCards(List<OrganizationEntity> organizations);

    List<CardOrganizationDto> cardsDto(List<OrganizationEntity> organizationCards);

}
