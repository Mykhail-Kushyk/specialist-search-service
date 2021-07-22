package com.ua.searchservice.repository;

import com.ua.searchservice.entity.customer.Customer;
import com.ua.searchservice.entity.order.Order;
import com.ua.searchservice.entity.specialist.Specialist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Query("SELECT o FROM Order o where o.customer = :customer")
    Page<Order> findByCustomer(Customer customer, Pageable pageable);

    @Transactional
    @Query("SELECT o FROM Order o where o.specialist is null")
    List<Order> findAvailableOrders();

    @Transactional
    @Query("SELECT o FROM Order o where o.specialist = :specialist")
    List<Order> findBySpecialist(Specialist specialist);

    @Transactional
    @Query("UPDATE Order o SET o.specialist = :specialist WHERE o.orderId = :orderId")
    @Modifying
    void setSpecialist(Long orderId, Specialist specialist);
}