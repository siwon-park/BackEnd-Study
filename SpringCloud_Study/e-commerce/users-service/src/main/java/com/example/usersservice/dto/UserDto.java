package com.example.usersservice.dto;

import com.example.usersservice.entity.UserEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String name;
    private String password;
    private String userId;
    private Date createdAt;

    private String encryptedPassword;
    private List<ResponseOrder> orders;

    UserEntity toEntity(String email, String name, String userId, String encryptedPassword) { // 엔티티 변환
        return UserEntity.builder()
                .userId(userId)
                .email(email)
                .name(name)
                .encryptedPasword(encryptedPassword)
                .build();
    }
}
