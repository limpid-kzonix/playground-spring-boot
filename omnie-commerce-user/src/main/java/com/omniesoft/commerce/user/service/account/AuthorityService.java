package com.omniesoft.commerce.user.service.account;

import com.omniesoft.commerce.persistence.entity.account.AuthorityEntity;
import com.omniesoft.commerce.persistence.entity.enums.AuthorityName;

public interface AuthorityService {
    AuthorityEntity getAuthority(AuthorityName name);
}
