package com.works.orderservice.service;

import com.works.orderservice.dto.OrderBasketSaveDto;
import com.works.orderservice.entity.OrderBasket;
import com.works.orderservice.entity.OrderItem;
import com.works.orderservice.repository.OrderBasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class OrderBasketService {

    final private OrderBasketRepository orderBasketRepository;

    public OrderBasket save(OrderBasketSaveDto orderBasketSaveDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderBasket orderBasket = objectMapper.convertValue(orderBasketSaveDto, OrderBasket.class);
        if (orderBasket.getItems() != null) {
            orderBasket.getItems().forEach(item -> item.setOrderBasket(orderBasket));
        }
        return orderBasketRepository.save(orderBasket);
    }

}
