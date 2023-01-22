package com.example.usersservice.service;

import com.example.usersservice.dto.ResponseOrder;
import com.example.usersservice.dto.UserDto;
import com.example.usersservice.entity.UserEntity;
import com.example.usersservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString()); // 유저 아이디에 랜덤 아이디 부여

        // Model Mapper 를 통한 UserDto -> UserEntity
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Model Mapper 가 매칭할 수 있는 정보에 대한 전략 => 정확히 맞아야 변환함
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPasword(bCryptPasswordEncoder.encode(userDto.getPassword())); // setter를 다른 것으로 변환하는 것이 좋을듯...? (리팩토링 예정)

        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not Found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }
}
