package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserEmailChangingVerificationEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 15.07.17
 */
@Transactional
public interface UserEmailChangingVerificationRepository extends CrudRepository<UserEmailChangingVerificationEntity, UUID> {

    UserEmailChangingVerificationEntity findByToken(String token);

    UserEmailChangingVerificationEntity findByUserId(UUID userId);

    void deleteByToken(String token);

    UserEmailChangingVerificationEntity findFirstByToken(String token);

    void deleteAllByCreateTimeBefore(LocalDateTime dateTime);
}
