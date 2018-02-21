package com.omniesoft.commerce.security.jwt;

import com.omniesoft.commerce.persistence.entity.account.AuthorityEntity;
import com.omniesoft.commerce.persistence.entity.enums.AuthorityName;

public interface AuthorityService {
    AuthorityEntity getAuthority(AuthorityName name);
}
