package com.example.userservice.api.service;

import com.example.userservice.api.dto.UserDto;
import com.example.userservice.api.entity.UserEntity;
import com.example.userservice.api.vo.RequestLogin;


public interface UserService {
    UserDto signup(UserDto userDto);

    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);

    String createToken(RequestLogin requestLogin);

    String getToken(String token);

}
