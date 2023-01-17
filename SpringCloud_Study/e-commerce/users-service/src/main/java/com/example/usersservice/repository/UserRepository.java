package com.example.usersservice.repository;

import com.example.usersservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// CrudRepository를 상속받아도 됨
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
