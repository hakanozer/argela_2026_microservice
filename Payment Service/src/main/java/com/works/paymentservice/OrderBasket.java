package com.works.paymentservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderBasket {

    private Long id;

    private Long customerId;

    private BigDecimal totalAmount;

    private OrderStatus status;

}
