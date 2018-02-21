package com.omniesoft.commerce.user.service.profile.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.common.payload.account.AccountDto;
import com.omniesoft.commerce.common.ws.imagestorage.ImageService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.account.UserProfileEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.user.controller.profile.payload.UserProfilePayload;
import com.omniesoft.commerce.user.service.profile.UserProfileService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

    private final ImageService imageService;

    private final ModelMapper modelMapper;


    @Override
    public AccountDto save(UserProfilePayload userProfile, UserEntity userEntity) {

        UserEntity withProfile = userRepository.findWithProfile(userEntity.getId());
        UserProfileEntity userProfileEntity = withProfile.getProfile();
        userProfileEntity = setProfile(userProfile, userProfileEntity);
        UserEntity source = saveUserWithProfile(userEntity, userProfileEntity);
        return modelMapper.map(source, AccountDto.class);
    }

    private UserProfileEntity setProfile(UserProfilePayload userProfile, UserProfileEntity userProfileEntity) {

        return userProfileEntity == null ? prepareProfile(userProfile) :
                prepareProfile(userProfile, userProfileEntity);
    }


    @Override
    public AccountDto find(UserEntity userEntity) {

        UserEntity withProfile = userRepository.findWithProfile(userEntity.getId());
        checkProfile(withProfile);
        return modelMapper.map(withProfile, AccountDto.class);
    }

    private void checkProfile(UserEntity withProfile) {

        if (withProfile == null) {
            throw new UsefulException(UserModuleErrorCodes.USER_NOT_EXIST);
        }
    }

    private String generateOmnieCard() {

        return "omnie#" + (int) ((System.nanoTime() / Math.PI) / (RandomUtils.nextInt(30000, 99999) * Math.E));
    }

    private UserProfileEntity prepareProfile(UserProfilePayload userProfile) {

        UserProfileEntity userProfileEntity;
        userProfileEntity = prepareProfile(userProfile, new UserProfileEntity());
        userProfileEntity.setOmnieCard(generateOmnieCard());
        return userProfileEntity;
    }

    private UserEntity saveUserWithProfile(UserEntity userEntity, UserProfileEntity userProfileEntity) {

        userEntity.setProfile(userProfileEntity);
        return userRepository.save(userEntity);
    }

    private UserProfileEntity prepareProfile(UserProfilePayload userProfile, UserProfileEntity userProfileEntity) {

        checkImage(userProfile, userProfileEntity);

        mapProfile(userProfile, userProfileEntity);
        return userProfileEntity;
    }

    private void mapProfile(UserProfilePayload userProfile, UserProfileEntity userProfileEntity) {

        userProfileEntity.setFirstName(userProfile.getFirstName());
        userProfileEntity.setLastName(userProfile.getLastName());
        userProfileEntity.setBirthday(userProfile.getBirthday());
        userProfileEntity.setGender(userProfile.getGender());
        userProfileEntity.setPhone(compactPhone(userProfile));
        userProfileEntity.setImageId(userProfile.getImageId());
    }

    private String compactPhone(UserProfilePayload userProfile) {

        return userProfile.getPhone().replaceAll("[^+0-9]", "");
    }

    private void checkImage(UserProfilePayload userProfile, UserProfileEntity userProfileEntity) {

        if (isImageIdDeletable(userProfile, userProfileEntity)) {
            imageService.deleteImage(userProfileEntity.getImageId());
        }
    }

    private boolean isImageIdDeletable(UserProfilePayload userProfile, UserProfileEntity userProfileEntity) {

        return userProfileEntity.getImageId() != null &&
                (userProfile.getImageId() == null || !userProfile.getImageId()
                        .equals(userProfileEntity.getImageId()));
    }
}
