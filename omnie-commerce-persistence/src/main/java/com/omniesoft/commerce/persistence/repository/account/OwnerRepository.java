package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.OwnerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.07.17
 */
public interface OwnerRepository extends CrudRepository<OwnerEntity, UUID>, OwnerRepositoryCustom {

}
