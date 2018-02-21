package com.omniesoft.commerce.persistence.repository.account.impl;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.repository.account.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 11.08.17
 */
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public UserEntity findWithProfile(UUID id) {
        return em.createQuery("select u" +
                        " from UserEntity u" +
                        " left join fetch u.profile" +
                        " where u.id=:id",
                UserEntity.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public UserEntity findWithProfile(String login) {
        return em.createQuery("select u" +
                        " from UserEntity u" +
                        " left join fetch u.profile" +
                        " where u.login=:login",
                UserEntity.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    @Override
    public UserEntity getUserSecurityDetails(String id) {
        return em.createQuery("select u" +
                        " from UserEntity u" +
                        " left join fetch u.authorities" +
                        " where u.login=:username or u.email=:username",
                UserEntity.class)
                .setParameter("username", id)
                .getSingleResult();
    }

    @Override
    public UserEntity merge(UserEntity user) {
        return em.merge(user);
    }
}