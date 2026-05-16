package com.works.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.works.orderservice.util.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class OrderBasket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private BigDecimal totalAmount;

    private OrderStatus status;

    //@JsonManagedReference
    //@OneToMany(mappedBy = "orderBasket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
