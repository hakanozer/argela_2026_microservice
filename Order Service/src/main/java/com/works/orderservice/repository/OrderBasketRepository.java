package com.works.orderservice.repository;

import com.works.orderservice.entity.OrderBasket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderBasketRepository extends JpaRepository<OrderBasket, Long> {

    List<OrderBasket> findAll();

    List<OrderBasket> findByCustomerIdEquals(Long customerId);
}