package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.entity.account.UserPasswordResetEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface UserPasswordRepository extends CrudRepository<UserPasswordResetEntity, UUID>, UserPasswordRepositoryCustom {

    UserPasswordResetEntity findByUserEmailAndCode(String userEmail, Integer code);

    UserPasswordResetEntity findByUserEmailAndResetToken(String userEmail, String resetToken);

    void deleteByUserEmail(String useremail);
}
