package com.omniesoft.commerce.persistence.entity.order;

import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 23.08.17
 */

@Entity
@Table(name = "order_sub_service_story")
public class OrderSubServicesStoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_story_id")
    private OrderStoryEntity orderStory;

    @ManyToOne
    @JoinColumn(name = "sub_service_id")
    private SubServiceEntity subService;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    @Column(name = "count")
    private Integer count;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "sub_service_price")
    private Double subServicePrice;

    @Column(name = "total_price")
    private Double totalPrice;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderStoryEntity getOrderStory() {
        return orderStory;
    }

    public void setOrderStory(OrderStoryEntity orderStory) {
        this.orderStory = orderStory;
    }

    public SubServiceEntity getSubService() {
        return subService;
    }

    public void setSubService(SubServiceEntity subService) {
        this.subService = subService;
    }

    public DiscountEntity getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountEntity discount) {
        this.discount = discount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getSubServicePrice() {
        return subServicePrice;
    }

    public void setSubServicePrice(Double subServicePrice) {
        this.subServicePrice = subServicePrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSubServicesStoryEntity that = (OrderSubServicesStoryEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderStory, that.orderStory);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderStory);
    }
}
