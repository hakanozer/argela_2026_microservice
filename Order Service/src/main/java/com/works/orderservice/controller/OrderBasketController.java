package com.works.orderservice.controller;

import com.works.orderservice.dto.OrderBasketDto;
import com.works.orderservice.dto.OrderBasketSaveDto;
import com.works.orderservice.entity.OrderBasket;
import com.works.orderservice.service.OrderBasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderBasketController {

    final private OrderBasketService orderBasketService;

    @PostMapping("save")
    public OrderBasket save(@Valid @RequestBody OrderBasketSaveDto orderBasketSaveDto) {
        return orderBasketService.save(orderBasketSaveDto);
    }

    @GetMapping("orderList")
    public List<OrderBasket> list(@RequestParam(defaultValue = "0") int page) {
        return orderBasketService.list(page);
    }

    @GetMapping("customerOrderList")
    public List<OrderBasket> listByCustomerId(@RequestParam long customerId,@RequestParam(defaultValue = "0") int page) {
        return orderBasketService.listByCustomerId(customerId, page);
    }

}
