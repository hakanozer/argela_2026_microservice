package com.works.orderservice.dto;

public record OrderItemDto(
        Long productId,
        int quantity,
        double price
) {}