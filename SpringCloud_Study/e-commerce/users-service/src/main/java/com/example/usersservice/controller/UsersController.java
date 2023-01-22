package com.example.usersservice.controller;

import com.example.usersservice.dto.RequestUserDto;
import com.example.usersservice.dto.ResponseUserDto;
import com.example.usersservice.dto.UserDto;
import com.example.usersservice.entity.UserEntity;
import com.example.usersservice.service.UserService;
import com.example.usersservice.vo.Greeting;
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
@RequestMapping("/users-service")
@RequiredArgsConstructor
public class UsersController {

    private final Environment env;
    private final Greeting greeting;

    private final UserService userService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome1")
    public String welcome1() {
        return env.getProperty("greetings.message");
    }

    @GetMapping("/welcome2")
    public String welcome2() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody RequestUserDto requestUserDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(requestUserDto, UserDto.class);
        userService.createUser(userDto);

        ResponseUserDto responseUserDto = mapper.map(userDto, ResponseUserDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDto); // return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUserDto>> getUsers() {
        Iterable<UserEntity> usersList = userService.getUserByAll();

        List<ResponseUserDto> result = new ArrayList<>();
        usersList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUserDto.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUserDto> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserById(userId);

        ResponseUserDto responseUserDto = new ModelMapper().map(userDto, ResponseUserDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseUserDto);
    }

}
