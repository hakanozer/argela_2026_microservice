package com.works.orderservice.repository;

import com.works.orderservice.entity.OrderBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBasketRepository extends JpaRepository<OrderBasket, Long> {
}