package com.omniesoft.commerce.persistence.entity.account;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 12.07.17
 */
@Entity
@Table(name = "user_email_changing_verification")
public class UserEmailChangingVerificationEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "new_email")
    private String email;

    @Column(name = "confirm_status")
    private Boolean confirmStatus;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createTime;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
