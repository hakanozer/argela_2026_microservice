package com.works.orderservice.event;

import com.works.orderservice.entity.OrderBasket;
import com.works.orderservice.entity.OrderItem;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Kafka üzerinden yayınlanan event.
 * IMMUTABLE olmalı (record kullan).
 * Geçmiş zaman ile isimlendir: OrderCREATED (oluşturuldu).
 * Versiyonlama için eventVersion alanı ekle.
 */
public record OrderCreatedEvent(
        UUID eventId,          // Idempotency için benzersiz event ID
        String eventVersion,   // "v1" - schema evriminde kritik
        Instant occurredAt,    // Event oluşturulma zamanı
        Long orderId,
        Long customerId,
        BigDecimal totalAmount
) {
    // Order Entity'den event oluşturucu
    public static OrderCreatedEvent from(OrderBasket order) {
        return new OrderCreatedEvent(
                UUID.randomUUID(),  // Her event için benzersiz ID
                "v1",
                Instant.now(),
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount()
        );
    }
}

// Item payload (entity'i doğrudan gönderme!)
record OrderItemPayload(
        Long productId,
        int quantity,
        double price
) {
    public static OrderItemPayload from(OrderItem item) {
        return new OrderItemPayload(
                item.getProductId(), item.getQuantity(),
                item.getPrice()
        );
    }
}
