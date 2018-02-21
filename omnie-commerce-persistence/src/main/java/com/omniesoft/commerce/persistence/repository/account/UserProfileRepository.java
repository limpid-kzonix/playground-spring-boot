package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserProfileEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface UserProfileRepository extends CrudRepository<UserProfileEntity, UUID> {
}
