package com.example.usersservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserDto {
    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;
}
