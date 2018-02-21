package com.omniesoft.commerce.persistence.dto.organization;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 21.08.17
 */
public class NewsRowDto {
    private UUID id;
    private String title;
    private Boolean promotionStatus;
    private LocalDateTime postDate;
    private LocalDateTime createDate;

    public NewsRowDto(UUID id, String title, Boolean promotionStatus, LocalDateTime postDate, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.promotionStatus = promotionStatus;
        this.postDate = postDate;
        this.createDate = createDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(Boolean promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
