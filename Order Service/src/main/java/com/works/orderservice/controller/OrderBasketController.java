package com.works.orderservice.controller;

import com.works.orderservice.dto.OrderBasketSaveDto;
import com.works.orderservice.entity.OrderBasket;
import com.works.orderservice.service.OrderBasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderBasketController {

    final private OrderBasketService orderBasketService;

    @PostMapping("save")
    public OrderBasket save(@Valid @RequestBody OrderBasketSaveDto orderBasketSaveDto) {
        return orderBasketService.save(orderBasketSaveDto);
    }

}
