package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
