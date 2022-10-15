package com.example.springsecurity.repository;

import com.example.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 갖고 있음
// @Repository라는 어노테이션이 없어도 IoC가 됨 => JpaRepository를 상속했기 때문
public interface UserRepository extends JpaRepository<User, Integer> {
    // select * from user where username = 1?
    public User findByUsername(String username);

    // select * from user where email = 1?
    public User findByEmail(String email);

}
