package com.works.orderservice.dto;

import com.works.orderservice.entity.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link OrderItem}
 */
@Data
public class OrderItemSaveDto {
    @NotNull
    Long productId;
    int quantity;
    double price;
}