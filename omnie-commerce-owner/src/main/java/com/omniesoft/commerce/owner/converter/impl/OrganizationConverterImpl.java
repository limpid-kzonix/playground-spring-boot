package com.omniesoft.commerce.owner.converter.impl;

import com.omniesoft.commerce.owner.controller.organization.payload.CardOrganizationDto;
import com.omniesoft.commerce.owner.controller.organization.payload.GalleryDto;
import com.omniesoft.commerce.owner.controller.organization.payload.OrganizationDto;
import com.omniesoft.commerce.owner.converter.OrganizationConverter;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationGalleryEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Martynovskyi
 * @since 04.10.17
 */
@Service
@AllArgsConstructor
public class OrganizationConverterImpl implements OrganizationConverter {

    private final ModelMapper mapper;

    @Override
    @Nullable
    public CardOrganizationDto cardDto(OrganizationEntity organization) {
        if (organization != null) {
            return mapper.map(organization, CardOrganizationDto.class);
        }
        return null;
    }

    @Override
    @Nullable
    public OrganizationDto organizationDto(OrganizationEntity organization) {
        if (organization != null) {
            return mapper.map(organization, OrganizationDto.class);
        }
        return null;
    }

    @Override
    public List<GalleryDto> gallery(Iterable<OrganizationGalleryEntity> galleryEntities) {
        if (galleryEntities != null) {
            List<GalleryDto> galleryDtos = new ArrayList<>();

            galleryEntities.forEach(organizationGalleryEntity ->
                    galleryDtos.add(mapper.map(organizationGalleryEntity, GalleryDto.class)));

            return galleryDtos;
        }
        return null;
    }

    @Override
    public List<CardOrganizationDto> organizationCards(List<OrganizationEntity> organizations) {
        if (organizations != null) {
            List<CardOrganizationDto> organizationCards = new ArrayList<>();

            organizations.forEach(organization ->
                    organizationCards.add(mapper.map(organization, CardOrganizationDto.class)));

            return organizationCards;
        }
        return null;
    }

    @Override
    public List<CardOrganizationDto> cardsDto(List<OrganizationEntity> organizations) {
        if (organizations != null) {

            java.lang.reflect.Type targetListType = new TypeToken<List<CardOrganizationDto>>() {
            }.getType();
            return mapper.map(organizations, targetListType);
        }
        return null;
    }


}
