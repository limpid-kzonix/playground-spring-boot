package com.omniesoft.commerce.persistence.entity.order;

import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.service.SubServiceEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Vitalii Martynovskyi
 * @since 23.08.17
 */

@Entity
@Table(name = "order_to_sub_service")
public class OrderSubServicesEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid")
    @Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "sub_service_id")
    private SubServiceEntity subService;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    @Column(name = "count")
    private Integer count;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "sub_service_price")
    private Double subServicePrice;

    @Column(name = "sub_service_expense")
    private Double subServiceExpense;

    @Column(name = "total_price")
    private Double totalPrice;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
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

    public Double getSubServiceExpense() {
        return subServiceExpense;
    }

    public void setSubServiceExpense(Double subServiceExpense) {
        this.subServiceExpense = subServiceExpense;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSubServicesEntity that = (OrderSubServicesEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, order);
    }
}
