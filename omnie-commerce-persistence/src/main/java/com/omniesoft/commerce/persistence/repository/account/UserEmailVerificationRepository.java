package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserEmailVerificationEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 15.07.17
 */
@Transactional
public interface UserEmailVerificationRepository extends CrudRepository<UserEmailVerificationEntity, UUID>, UserEmailVerificationRepositoryCustom {

    void deleteByToken(String token);

    UserEmailVerificationEntity findFirstByUserEmail(String email);

    void deleteAllByCreateTimeBefore(LocalDateTime dateTime);
}
