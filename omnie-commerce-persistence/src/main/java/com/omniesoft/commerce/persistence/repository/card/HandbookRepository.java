package com.omniesoft.commerce.persistence.repository.card;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookEntity;
import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface HandbookRepository extends PagingAndSortingRepository<HandbookEntity, UUID> {
    @Query(value =
            "" +
                    "select " +
                    " h " +
                    "from HandbookEntity as h " +
                    "   left join fetch h.tags tags " +
                    "   left join fetch h.phones phones " +
                    "where " +
                    "   lower(h.name) like %:filter% " +
                    "   or lower(tags.tag) like %:filter%" +
                    "   or lower(phones.phone) like %:filter% " +
                    "" +
                    "",
            countQuery = "" +
                    "select " +
                    " count (h) " +
                    "from HandbookEntity as h " +
                    "   left join h.tags tags " +
                    "   left join h.phones phones " +
                    "where " +
                    "   lower(h.name) like %:filter% " +
                    "   or lower(tags.tag) like %:filter% " +
                    "   or lower(phones.phone) like %:filter% " +
                    "" +
                    ""
    )
    Page<HandbookSummary> findHandbookItemsWithPhonesAndTags(@Param("filter") String filter, Pageable pageable);

    @Query(value =
            "" +
                    "select " +
                    " h " +
                    "from HandbookEntity h " +
                    "   left join fetch h.tags tags " +
                    "   left join fetch h.phones phones " +
                    "   left join h.userEntity " +
                    "where ( " +
                    "   lower(h.name) like %:filter% " +
                    "   or lower(tags.tag) like %:filter% " +
                    "   or lower(phones.phone) like %:filter% )" +
                    "   and h.userEntity = :user " +
                    "" +
                    "",
            countQuery = "" +
                    "select " +
                    " count(h) " +
                    "from HandbookEntity h " +
                    "   left join h.tags tags " +
                    "   left join h.phones phones " +
                    "where ( " +
                    "   lower(h.name) like %:filter% " +
                    "   or lower(tags.tag) like %:filter% " +
                    "   or lower(phones.phone) like %:filter% )" +
                    "   and h.userEntity = :user " +
                    ""
    )
    Page<HandbookSummary> findAllByUserEntity(@Param("filter") String filter, @Param("user") UserEntity user, Pageable pageable);

    HandbookEntity findByUserEntityAndId(UserEntity userEntity, UUID id);

    HandbookSummary findById(UUID id);

    @Query("select h from HandbookEntity h join fetch h.phones p join fetch h.tags t where h.id=:id")
    HandbookEntity findFetchAll(@Param("id") UUID id);
}
