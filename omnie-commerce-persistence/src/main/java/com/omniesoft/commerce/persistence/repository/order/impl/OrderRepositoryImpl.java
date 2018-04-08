package com.omniesoft.commerce.persistence.repository.order.impl;

import com.omniesoft.commerce.persistence.entity.enums.OrderStatus;
import com.omniesoft.commerce.persistence.entity.order.OrderEntity;
import com.omniesoft.commerce.persistence.repository.order.OrderRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static com.omniesoft.commerce.persistence.entity.enums.OrderStatus.*;

/**
 * @author Vitalii Martynovskyi
 * @since 20.11.17
 */
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<OrderEntity> findReadyToProcessing(LocalDateTime start, LocalDateTime end, UUID serviceId) {
        return em.createQuery(
                "select o" +
                        " from OrderEntity o" +
                        " inner join fetch o.service" +
                        " left join fetch o.user" +
                        " left join fetch o.subServices" +
                        " where o.service.id = :serviceId" +
                        " and o.end between :start and :ends" +
                        " and (o.status = :PFU " +
                        " or  o.status = :PFA " +
                        " or  o.status = :CBU" +
                        " or  o.status = :CBA" +
                        " or  o.status = :DONE )",
                OrderEntity.class)
                .setParameter("serviceId", serviceId)
                .setParameter("start", start)
                .setParameter("ends", end)
                .setParameter("PFU", PENDING_FOR_USER)
                .setParameter("PFA", PENDING_FOR_ADMIN)
                .setParameter("CBU", CONFIRM_BY_USER)
                .setParameter("CBA", CONFIRM_BY_ADMIN)
                .setParameter("DONE", DONE)
                .getResultList();

    }

    @Override
    public List<OrderEntity> findAccepted(LocalDateTime start, LocalDateTime end, UUID serviceId) {
        return em.createQuery(
                "select o" +
                        " from OrderEntity o" +
                        " inner join fetch o.service" +
                        " left join fetch o.user" +
                        " left join fetch o.subServices" +
                        " where o.service.id = :serviceId" +
                        " and o.end between :start and :ends" +
                        " and ( o.status = :CBU" +
                        " or  o.status = :CBA" +
                        " or  o.status = :DONE)",
                OrderEntity.class)
                .setParameter("serviceId", serviceId)
                .setParameter("start", start)
                .setParameter("ends", end)
                .setParameter("CBU", CONFIRM_BY_USER)
                .setParameter("CBA", CONFIRM_BY_ADMIN)
                .setParameter("DONE", DONE)

                .getResultList();
    }

    @Override
    public LocalDateTime findLastDateOfOrderForService(UUID service) {

        LocalDateTime singleResult = em.createQuery(
                "select max(o.end) from OrderEntity o" +
                        " where o.service.id = :serviceId" +
                        " and o.status = :CBA or o.status = :CBU or o.status = :D",
                LocalDateTime.class)
                .setParameter("serviceId", service)
                .setParameter("CBA", OrderStatus.CONFIRM_BY_ADMIN)
                .setParameter("CBU", OrderStatus.CONFIRM_BY_USER)
                .setParameter("D", OrderStatus.DONE)
                .getSingleResult();
        if (singleResult == null) {
            return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        }
        return singleResult;
    }

    @Override
    public List<LocalDateTime> findAllDayDateWithOrders(UUID userId, LocalDate startDate, LocalDate endDate) {
        return em.createQuery(
                "select o.start from OrderEntity o" +
                        "   where o.start between :startDate and :endDate ",
                LocalDateTime.class)
                .setParameter("startDate", LocalDateTime.of(startDate, LocalTime.MIN))
                .setParameter("endDate", LocalDateTime.of(endDate, LocalTime.MAX))
                .getResultList();
    }
}
