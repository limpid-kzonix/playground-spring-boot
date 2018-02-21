package com.omniesoft.commerce.persistence.repository.conversation.impl;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.conversation.MessageEntity;
import com.omniesoft.commerce.persistence.entity.organization.OrganizationEntity;
import com.omniesoft.commerce.persistence.repository.conversation.MessageRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

public class MessageRepositoryImpl implements MessageRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public void readMessages(UUID conversation, UserEntity userEntity) {
        em.createQuery("" +
                "update MessageEntity m " +
                "   set m.readUserStatus = true " +
                "where (m.conversation.user = :user ) and " +
                "  " +
                "   (m.conversation.id = :conversation) and " +
                "   (m.readUserStatus = false)", MessageEntity.class)
                .setParameter("user", userEntity)
                .setParameter("conversation", conversation)
                .executeUpdate();
    }

    @Override
    public void readMessages(UUID conversation, OrganizationEntity organizationEntity, UserEntity userEntity) {
        em.createQuery("" +
                "update MessageEntity m " +
                "   set m.readOrgStatus = true " +
                "where (m.conversation.organization = :org) and " +
                "    " +
                "   (m.conversation.user = m.sender) and " +
                "   (m.conversation.id = :conversation) and" +
                "   (m.readOrgStatus = false)", MessageEntity.class)
                .setParameter("org", organizationEntity)
                .setParameter("conversation", conversation)
                .executeUpdate();
    }
}
