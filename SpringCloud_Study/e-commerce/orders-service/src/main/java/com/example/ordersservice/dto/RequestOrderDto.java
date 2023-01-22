package com.example.ordersservice.dto;

import lombok.Data;

@Data
public class RequestOrderDto {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
}
