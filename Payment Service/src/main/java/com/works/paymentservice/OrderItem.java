package com.works.paymentservice;

import lombok.Data;

@Data
public class OrderItem {

    private Long id;

    private OrderBasket orderBasket;

    private Long productId;
    private int quantity;
    private double price;

}
