package com.example.usersservice.client;

import com.example.usersservice.dto.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="orders-service") // 호출하려는 마이크로서비스의 이름을 작성
public interface OrderServiceClient {

    @GetMapping("/orders-service/{userId}/orders") // 호츌하려는 서비스의 요청 주소
    List<ResponseOrder> getOrders(@PathVariable String userId);
}

