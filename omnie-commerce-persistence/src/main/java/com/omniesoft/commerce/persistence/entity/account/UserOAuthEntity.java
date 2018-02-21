package com.omniesoft.commerce.persistence.entity.account;

import com.omniesoft.commerce.persistence.entity.enums.OAuthClient;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 17.09.17
 */
@Entity
@Table(name = "user_oauth", uniqueConstraints = {@UniqueConstraint(columnNames = {"oauth_client", "user_id"})})
public class UserOAuthEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "oauth_client")
    @Enumerated(EnumType.ORDINAL)
    private OAuthClient oauthClient;

    @Column(name = "profile_id")
    private String profileId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OAuthClient getOauthClient() {
        return oauthClient;
    }

    public void setOauthClient(OAuthClient oauthClient) {
        this.oauthClient = oauthClient;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
