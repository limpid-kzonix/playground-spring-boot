package com.omniesoft.commerce.owner.config.mapping;

import com.omniesoft.commerce.owner.controller.organization.payload.DiscountAssociationDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserProfileEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 27.10.17
 */
@Configuration
public class ModelMapperConfig {
    private PropertyMap<UserEntity, DiscountAssociationDto> UserEntity_DiscountAssociationDto =
            new PropertyMap<UserEntity, DiscountAssociationDto>() {
                Converter<UserEntity, String> concatName =
                        context -> {
                            UserProfileEntity profile = context.getSource().getProfile();
                            if (profile != null) {
                                return profile.getFirstName() + " " + profile.getLastName();
                            }
                            return null;
                        };

                Converter<UserEntity, UUID> id = context -> context.getSource().getId();

                protected void configure() {
                    using(concatName).map(source).setName(null);
                    using(id).map(source).setId(null);

                }
            };

    @ConditionalOnMissingBean(name = "modelMapper")
    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        modelMapper.addMappings(UserEntity_DiscountAssociationDto);
        return modelMapper;
    }

}
