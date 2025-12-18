package com.bookbazar.bookstore.repository;

import com.bookbazar.bookstore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Allows viewing paginated order history for a specific user (Requirement 3.77)
    Page<Order> findByUserId(Long userId, Pageable pageable);
}