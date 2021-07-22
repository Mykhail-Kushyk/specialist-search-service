package com.ua.searchservice.repository;

import com.ua.searchservice.entity.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);

    boolean existsByUsername(String username);
}