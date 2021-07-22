package com.ua.searchservice.repository;

import com.ua.searchservice.entity.order.Order;
import com.ua.searchservice.entity.specialist.Specialist;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    @Transactional
    Optional<Specialist> readByUsername(String username);

    @Transactional
    boolean existsSpecialistByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    @Transactional
    @Query(value = "SELECT * FROM users s join service_proposals sp on s.user_id = sp.specialist_id " +
            "AND sp.order_id = :orderId",
            nativeQuery = true)
    Page<Specialist> findProposedSpecialistsByOrder(Long orderId, Pageable pageable);
}