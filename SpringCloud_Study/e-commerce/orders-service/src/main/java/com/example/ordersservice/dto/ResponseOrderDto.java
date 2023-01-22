package com.example.ordersservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrderDto {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
}
