package com.omniesoft.commerce.persistence.repository.account;

import com.omniesoft.commerce.persistence.dto.account.UserRowDto;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.account.AccountProfileDataSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 06.06.17
 */
@Transactional
public interface UserRepository extends PagingAndSortingRepository<UserEntity, UUID>, UserRepositoryCustom {

    UserEntity findByLogin(String login);

    UserEntity findByLoginOrEmail(String login, String email);

    UserEntity findByEmail(String email);

    Page<AccountProfileDataSummary> findByEmailContaining(String email, Pageable pageable);

    Page<AccountProfileDataSummary> findByProfileOmnieCardContaining(String omnieCard, Pageable pageable);

    Page<AccountProfileDataSummary> findByProfileFirstNameContainingAndProfileLastNameContaining(String firstName, String
            lastName, Pageable pageable);

    Page<AccountProfileDataSummary> findByProfilePhoneContaining(String phone, Pageable pageable);

    List<UserEntity> findByLoginContaining(String login);

    @Query(value = "SELECT COUNT(u) FROM UserEntity u WHERE u.login LIKE %?1%")
    Long getCountByLogin(String login);

    @Query(value = "SELECT u FROM UserEntity u WHERE u.login LIKE %?1%")
    Page<UserEntity> getRandomUserByLogin(String login, Pageable page);

    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.account.UserRowDto( " +
            " u.id," +
            " u.login," +
            " u.email," +
            " u.registrationDate," +
            " p.firstName," +
            " p.lastName)" +
            " FROM UserEntity u LEFT JOIN u.profile p")
    Page<UserRowDto> getUsersMainInfo(Pageable page);

    @Query(value = "SELECT new com.omniesoft.commerce.persistence.dto.account.UserRowDto( " +
            " u.id," +
            " u.login," +
            " u.email," +
            " u.registrationDate," +
            " p.firstName," +
            " p.lastName)" +
            " FROM UserEntity u LEFT JOIN u.profile p" +
            " WHERE lower(u.login)     LIKE ?1% " +
            "      OR lower(u.email)   LIKE ?1%" +
            "      OR lower(firstName) LIKE ?1%" +
            "      OR lower(lastName)   LIKE ?1%")
    Page<UserRowDto> getUsersMainInfo(String filter, Pageable page);

    AccountProfileDataSummary findById(UUID id);

    List<UserEntity> findAllById(List<UUID> ids);
}