package com.ua.searchservice.repository;

import com.ua.searchservice.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional
    Optional<Customer> findByUsername(String username);

    @Transactional
    boolean existsCustomerByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}