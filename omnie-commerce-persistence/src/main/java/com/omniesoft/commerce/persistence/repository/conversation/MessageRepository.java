package com.omniesoft.commerce.persistence.repository.conversation;

import com.omniesoft.commerce.persistence.entity.conversation.MessageEntity;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Transactional
public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, UUID>, MessageRepositoryCustom {

    @Query(value = "" +
            "select " +
            "   mes " +
            "from MessageEntity mes " +
            "   left join mes.sender" +
            "   left join mes.conversation con " +
            "where " +
            "   (con.id = :id and lower(mes.message) like %:filter%) " +
            "order by " +
            "   mes.createTime asc ",
            countQuery = "" +
                    "select " +
                    "   count (mes) " +
                    "from MessageEntity mes " +
                    "   left join mes.conversation con " +
                    "where " +
                    "   con.id = :id and lower(mes.message) like %:filter% "

    )
    Page<MessageSummary> getMessagesPage(@Param("id") UUID conversationId, @Param("filter") String filter, Pageable pageable);


    @Query(value = "" +
            "select " +
            "   mes " +
            "from MessageEntity mes " +
            "   left join fetch mes.sender" +
            "   left join fetch mes.conversation con " +
            "   left join fetch con.user user " +
            "   left join fetch con.organization org " +
            "where " +
            "   (user.login = :username and mes.readUserStatus = false ) " +
            "group by " +
            "   con.id, " +
            "   org.id " +
            "having  mes.createTime = max (mes.createTime)"


    )
    List<MessageSummary> getUnreadMessagesForUser(@Param("username") String userName);

    @Query(value = "" +
            "select " +
            "   mes " +
            "from MessageEntity mes " +
            "   left join fetch mes.sender" +
            "   left join fetch mes.conversation con " +
            "   left join fetch con.user user " +
            "   left join fetch con.organization org " +
            "where " +
            "   (org.id = :org and mes.readOrgStatus = false ) " +
            "group by " +
            "   con.id, " +
            "   org.id " +
            "having mes.createTime = max(mes.createTime)"
    )
    List<MessageSummary> getUnreadMessagesForOrganization(@Param("org") UUID org);


    @Query(value = "" +
            "select " +
            "   mes " +
            "from MessageEntity mes " +
            "   left join fetch mes.sender sender " +
            "   left join mes.conversation con " +
            "where mes.createTime <= :date" +
            "      and con.id = :conversation " +
            "group by  mes.id, sender.id, con.id " +
            "order by " +
            "   mes.createTime asc " +
            "",
            countQuery = "" +
                    "select " +
                    "   count(mes) " +
                    "from MessageEntity mes " +
                    " " +
                    "   left join mes.conversation con " +
                    "where mes.createTime <= :date" +
                    "      and con.id = :conversation ")
    Page<MessageSummary> findPreviousMessage(@Param("conversation") UUID conversationId, @Param("date") LocalDateTime date, Pageable pageable);


    @Query(value = "" +
            "select " +
            "   mes " +
            "from MessageEntity as mes " +
            "   left join mes.sender as sender " +
            "   left join mes.conversation as con " +
            "where mes.createTime >= :date" +
            "      and con.id = :conversation " +
            "group by mes.id, sender.id, con.id   " +
            "order by " +
            "   mes.createTime desc " +
            "",
            countQuery = "" +
                    "select " +
                    "   count(mes)" +
                    "from MessageEntity as mes " +
                    "    " +
                    "   left join mes.conversation as con " +
                    "where mes.createTime >= :date" +
                    "      and con.id = :conversation ")
    Page<MessageSummary> findNextMessage(@Param("conversation") UUID conversationId, @Param("date") LocalDateTime date, Pageable pageable);

    MessageEntity findById(UUID message);

    MessageSummary findTopByConversationIdOrderByCreateTimeDesc(UUID conversationId);


}
