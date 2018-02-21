package com.omniesoft.commerce.owner.service.security.impl;

import com.omniesoft.commerce.common.handler.exception.custom.UsefulException;
import com.omniesoft.commerce.common.handler.exception.custom.enums.UserModuleErrorCodes;
import com.omniesoft.commerce.owner.service.security.SecurityContextService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    private UserRepository userRepository;

    @Autowired
    public SecurityContextServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUser() {

        String userDetailsName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserEntity byLogin = userRepository.findByLogin(userDetailsName);
        if (!byLogin.getEnabled()) {
            throw new UsefulException("User account is not activate. Confirm registration please",
                    UserModuleErrorCodes.USER_NOT_ACTIVE);
        }
        return byLogin;

    }

    @Override
    public boolean isAuthorized() {
        return false;
    }
}
