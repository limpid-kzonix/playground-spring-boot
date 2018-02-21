package com.omniesoft.commerce.persistence.repository.conversation;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.conversation.ConversationEntity;
import com.omniesoft.commerce.persistence.projection.conversation.ConversationSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@Repository
public interface ConversationRepository extends PagingAndSortingRepository<ConversationEntity, UUID> {


    @Query(value = "" +
            "select " +
            "   con " +
            "from ConversationEntity con " +
            "   left join fetch con.organization org" +
            "   left join fetch con.user user " +
            "   left join fetch user.profile profile " +
            "   left join fetch con.messages mes " +
            "where " +
            "   user = :user and lower(org.name) like %:filter% " +
            "group by " +
            "   con.id," +
            "   org.id," +
            "   user.id," +
            "   profile.id," +
            "   mes.id " +
            "having" +
            "   mes = null or mes.createTime = max(mes.createTime)" +
            "",
            countQuery = "" +
                    "select " +
                    "   count (con) " +
                    "from ConversationEntity con " +
                    "   left join con.user user" +
                    "   left join con.organization org " +
                    "where " +
                    "   user = :user and org.name like %:filter%"
    )
    Page<ConversationSummary> findFullConversationData(@Param("user") UserEntity userEntity,
                                                       @Param("filter") String filter, Pageable pageable);

    @Query(value = "" +
            "select " +
            "   con " +
            "from ConversationEntity con " +
            "   left join con.organization org" +
            "   left join fetch con.user user " +
            "   left join fetch user.profile profile " +
            "   left join fetch con.messages mes " +
            "where " +
            "   org.id = :organization and " +
            "   (" +
            "       lower(user.email) like %:filter% " +
            "       or lower(user.login) like %:filter% " +
            "       or concat(lower(profile.firstName), ' ', lower(profile.lastName)) like %:filter% " +
            "   )" +
            "   " +
            "group by " +
            "   con.id," +
            "   org.id," +
            "   user.id," +
            "   profile.id," +
            "   mes.id " +
            "having" +
            "   mes = null or mes.createTime = max(mes.createTime)" +
            "",
            countQuery = "" +
                    "select " +
                    "   count (con) " +
                    "from ConversationEntity con " +
                    "   left join con.user user " +
                    "   left join user.profile profile " +
                    "   left join con.organization org " +
                    "where " +
                    "   org.id = :organization and " +
                    "   (" +
                    "       lower(user.email) like %:filter% " +
                    "       or lower(user.login) like %:filter% " +
                    "       or concat(lower(profile.firstName), ' ', lower(profile.lastName)) like %:filter% " +
                    "   )"
    )
    Page<ConversationSummary> findConversationsInfoWithUser(
            @Param("organization") UUID orgId,
            @Param("filter") String filter,
            Pageable pageable);


    @Query(value = "" +
            "select " +
            "   con " +
            "from ConversationEntity con " +
            "   left join fetch con.organization org" +
            "   left join con.user user " +
            "   left join fetch con.messages mes " +
            "where " +
            "   user = :user and lower(org.name) like %:filter% " +
            "group by " +
            "   con.id," +
            "   org.id," +
            "   user.id," +
            "   mes.id " +
            "having" +
            "    mes = null or mes.createTime = max(mes.createTime)" +
            "",
            countQuery = "" +
                    "select " +
                    "   count (con) " +
                    "from ConversationEntity con " +
                    "   left join con.user user" +
                    "   left join con.organization org " +
                    "where " +
                    "   user = :user and lower(org.name) like %:filter%"
    )
    Page<ConversationSummary> findConversationsInfoWithOrganization(@Param("user") UserEntity userEntity, @Param
            ("filter")
            String
            filter, Pageable pageable);


    Page<ConversationSummary> findAllByUser(UserEntity userEntity, Pageable pageable);

    Page<ConversationSummary> findAllByOrganizationId(UUID organization, Pageable pageable);

    ConversationEntity findByUserAndId(UserEntity userEntity, UUID id);

    ConversationEntity findByOrganizationIdAndId(UUID org, UUID conversation);


}
