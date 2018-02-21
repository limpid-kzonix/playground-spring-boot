package com.omniesoft.commerce.persistence.repository.account.impl;

import com.omniesoft.commerce.persistence.entity.account.UserEmailVerificationEntity;
import com.omniesoft.commerce.persistence.repository.account.UserEmailVerificationRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserEmailVerificationCustomRepositoryImpl implements UserEmailVerificationRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserEmailVerificationEntity findByToken(String token) {
        return em.createQuery("select u" +
                        " from UserEmailVerificationEntity email" +
                        " left join fetch email.user" +
                        " where email.token=:token",
                UserEmailVerificationEntity.class)
                .setParameter("token", token)
                .getSingleResult();
    }
}
