package com.omniesoft.commerce.persistence.entity.order;

import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.discount.DiscountEntity;
import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.entity.service.ServiceEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

/**
 * @author Vitalii Martynovskyi
 * @since 23.08.17
 */
@Entity
@Table(name = "order_story")
public class OrderStoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "start_time")
    private LocalDateTime start;

    @Column(name = "end_time")
    private LocalDateTime end;

    @Column(name = "comment")
    private String comment;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "service_price")
    private Double servicePrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "create_time", insertable = false, updatable = false)
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "change_by")
    private UserEntity changeByUser;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {PERSIST, REFRESH})
    private List<OrderSubServicesStoryEntity> subServicesStory;

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DiscountEntity getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountEntity discount) {
        this.discount = discount;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public UserEntity getChangeByUser() {
        return changeByUser;
    }

    public void setChangeByUser(UserEntity changeByUser) {
        this.changeByUser = changeByUser;
    }

    public List<OrderSubServicesStoryEntity> getSubServicesStory() {
        return subServicesStory;
    }

    public void setSubServicesStory(List<OrderSubServicesStoryEntity> subServicesStory) {
        this.subServicesStory = subServicesStory;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
