package com.example.userservice.api.dto;

import com.example.userservice.api.vo.ResponseAuth;
import com.example.userservice.api.vo.ResponseOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private Date createdAt;
    private String decryptedPwd;
    private String encryptedPwd;
    private String auth;
    private List<ResponseAuth> auths;
    private List<ResponseOrder> orders;
}
