package com.omniesoft.commerce.persistence.repository.account.impl;

import com.omniesoft.commerce.persistence.entity.account.UserPasswordResetEntity;
import com.omniesoft.commerce.persistence.repository.account.UserPasswordRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserPasswordRepositoryImpl implements UserPasswordRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserPasswordResetEntity findWithUser(String userEmail, Integer code) {
        return em.createQuery("select upre" +
                        " from UserPasswordResetEntity upre" +
                        " left join fetch upre.user" +
                        " where upre.user.email=:email" +
                        " and upre.code=:code",
                UserPasswordResetEntity.class)
                .setParameter("email", userEmail)
                .setParameter("code", code)
                .getSingleResult();
    }

    @Override
    public UserPasswordResetEntity findWithUser(String userEmail, String resetToken) {
        return em.createQuery("select upre" +
                        " from UserPasswordResetEntity upre" +
                        " left join fetch upre.user" +
                        " where upre.user.email=:email" +
                        " and upre.resetToken=:token",
                UserPasswordResetEntity.class)
                .setParameter("email", userEmail)
                .setParameter("token", resetToken)
                .getSingleResult();
    }
}
