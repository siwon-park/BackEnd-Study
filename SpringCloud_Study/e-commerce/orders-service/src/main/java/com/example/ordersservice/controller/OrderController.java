package com.example.ordersservice.controller;

import com.example.ordersservice.dto.OrderDto;
import com.example.ordersservice.dto.RequestOrderDto;
import com.example.ordersservice.dto.ResponseOrderDto;
import com.example.ordersservice.entity.OrderEntity;
import com.example.ordersservice.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders-service")
@RequiredArgsConstructor
public class OrderController {
    private final Environment env;
    private final OrderServiceImpl orderService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrderDto> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrderDto requestOrderDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(requestOrderDto, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createdOrder = orderService.createOrder(orderDto);

        ResponseOrderDto responseOrderDto = mapper.map(createdOrder, ResponseOrderDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrderDto);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrderDto>> getOrders(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);
        List<ResponseOrderDto> result = new ArrayList<>();

        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrderDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
