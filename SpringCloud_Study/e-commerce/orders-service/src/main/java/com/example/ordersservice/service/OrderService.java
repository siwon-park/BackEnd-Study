package com.example.ordersservice.service;


import com.example.ordersservice.dto.OrderDto;
import com.example.ordersservice.entity.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
