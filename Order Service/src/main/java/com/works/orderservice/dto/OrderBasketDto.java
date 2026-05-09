package com.works.orderservice.dto;

import com.works.orderservice.util.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderBasketDto(
        Long id,
        Long customerId,
        BigDecimal totalAmount,
        OrderStatus status,
        List<OrderItemDto> items
) {}