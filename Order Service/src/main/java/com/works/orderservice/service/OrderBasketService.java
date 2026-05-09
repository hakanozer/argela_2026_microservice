package com.works.orderservice.service;

import com.works.orderservice.dto.OrderBasketSaveDto;
import com.works.orderservice.entity.OrderBasket;
import com.works.orderservice.entity.OrderItem;
import com.works.orderservice.repository.OrderBasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderBasketService {

    final private OrderBasketRepository orderBasketRepository;

    @CacheEvict(cacheNames = "orderBaskets", allEntries = true)
    public OrderBasket save(OrderBasketSaveDto orderBasketSaveDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderBasket orderBasket = objectMapper.convertValue(orderBasketSaveDto, OrderBasket.class);
        //if (orderBasket.getItems() != null) {
          //  orderBasket.getItems().forEach(item -> item.setOrderBasket(orderBasket));
        //}
        return orderBasketRepository.save(orderBasket);
    }

    // sayfalamalı - page order listesi
    @Cacheable(cacheNames = "orderBaskets", key = "'page:' + #page")
    public List<OrderBasket> list(int page) {
        return orderBasketRepository.findAll();
    }

    // customerId ile order basket getirme
    @Cacheable(cacheNames = "orderBaskets", key = "'customer:' + #customerId + ':page:' + #page")
    public List<OrderBasket> listByCustomerId(long customerId, int page) {
        return orderBasketRepository.findByCustomerIdEquals(customerId);
    }

}
