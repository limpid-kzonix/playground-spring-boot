package com.omniesoft.commerce.security.jwt.service;

import com.omniesoft.commerce.common.util.Patterns;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepository;
import com.omniesoft.commerce.security.jwt.JsonWebTokenUserFactory;
import com.omniesoft.commerce.security.jwt.payload.JsonWebTokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JsonWebTokenUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public JsonWebTokenUser loadUserByUsername(String userName) {
        if (!userName.matches(Patterns.EMAIL)) {
            userName = userName.toLowerCase();
        }
        UserEntity user = userRepository.getUserSecurityDetails(userName);

        if (null == user) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
        } else {
            return JsonWebTokenUserFactory.create(user);
        }
    }
}


