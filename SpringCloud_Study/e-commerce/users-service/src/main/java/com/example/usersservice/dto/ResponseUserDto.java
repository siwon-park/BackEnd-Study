package com.example.usersservice.dto;

import lombok.Data;

@Data
public class ResponseUserDto {
    private String email;
    private String name;
    private String userId;
}
