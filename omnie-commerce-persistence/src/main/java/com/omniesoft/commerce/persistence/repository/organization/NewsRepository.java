package com.omniesoft.commerce.persistence.repository.organization;

import com.omniesoft.commerce.persistence.dto.organization.NewsRowDto;
import com.omniesoft.commerce.persistence.entity.organization.NewsEntity;
import com.omniesoft.commerce.persistence.projection.organization.NewsExtendedSummary;
import com.omniesoft.commerce.persistence.projection.organization.NewsSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 21.08.17
 */
@Transactional
public interface NewsRepository extends PagingAndSortingRepository<NewsEntity, UUID>, NewsRepositoryCustom {

    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.organization.NewsRowDto( " +
            " n.id," +
            " n.title," +
            " n.promotionStatus," +
            " n.postTime," +
            " n.createTime)" +
            " FROM NewsEntity as n" +
            " WHERE n.organization.id = :id")
    Page<NewsRowDto> getNewsPageByOrganization(@Param("id") UUID id, Pageable page);

    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.organization.NewsRowDto( " +
            " n.id," +
            " n.title," +
            " n.promotionStatus," +
            " n.postTime," +
            " n.createTime)" +
            " FROM NewsEntity as n" +
            " WHERE n.organization.id = :id" +
            " AND n.title LIKE %:filter%")
    Page<NewsRowDto> getNewsPageByOrganization(@Param("id") UUID id,
                                               @Param("filter") String filter,
                                               Pageable page);


    Page<NewsSummary> findAllByOrganizationIdAndPromotionStatus(UUID organizationId,
                                                                Boolean status,
                                                                Pageable pageable);

    @Query(value = "" +
            " select n from NewsEntity n " +
            " left join fetch n.services " +
            " " +
            " where n.promotionStatus = :status " +
            "   and n.organization.id in :orgs " +
            "   and n.postTime <= :currentTime" +
            " " +
            "  ",
            countQuery = " select count (n) from NewsEntity n " +
            " " +
            " where n.promotionStatus     =:status " +
            " and n.organization.id in :orgs" +
            " and n.postTime <= :currentTime " +
            " ")
    Page<NewsSummary> findAllByOrganizationIdAndPromotionStatusForUser(
            @Param("status") Boolean status,
            @Param("orgs") List<UUID> orgs,
            @Param("currentTime") LocalDateTime currentTime,
            Pageable pageable);

    NewsExtendedSummary findByIdAndPromotionStatusIsFalse(UUID id);

    NewsExtendedSummary findByIdAndPromotionStatusIsTrue(UUID id);


}