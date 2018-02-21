package com.omniesoft.commerce.security.jwt;

import com.omniesoft.commerce.persistence.entity.account.AuthorityEntity;
import com.omniesoft.commerce.persistence.entity.enums.AuthorityName;
import com.omniesoft.commerce.persistence.repository.account.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {


    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public AuthorityEntity getAuthority(AuthorityName name) {
        AuthorityEntity byName = authorityRepository.findByName(name);
        if (null == byName) {
            AuthorityEntity authority = new AuthorityEntity();
            authority.setName(name);
            byName = authorityRepository.save(authority);
        }
        return byName;
    }
}
