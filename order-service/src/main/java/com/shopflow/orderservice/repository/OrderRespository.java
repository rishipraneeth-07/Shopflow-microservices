package com.shopflow.orderservice.repository;

import com.shopflow.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRespository extends JpaRepository<Order, Long> {
}
