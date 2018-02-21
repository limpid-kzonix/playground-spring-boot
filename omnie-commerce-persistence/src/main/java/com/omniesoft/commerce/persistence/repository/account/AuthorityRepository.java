package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.AuthorityEntity;
import com.omniesoft.commerce.persistence.entity.enums.AuthorityName;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AuthorityRepository extends PagingAndSortingRepository<AuthorityEntity, UUID> {

    AuthorityEntity findByName(AuthorityName name);
}
