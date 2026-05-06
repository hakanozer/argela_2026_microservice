package com.works.orderservice.dto;

import com.works.orderservice.util.OrderStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link com.works.orderservice.entity.OrderBasket}
 */
@Data
public class OrderBasketSaveDto  {
    @NotNull
    @Min(1)
    Long customerId;
    @NotNull
    @Min(1)
    @Max(1000000)
    BigDecimal totalAmount;
    @NotNull
    OrderStatus status;
    @NotNull
    @Size(min = 1, max = 500)
    List<OrderItemSaveDto> items;
}