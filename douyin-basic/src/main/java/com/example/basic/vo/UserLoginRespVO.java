package com.example.basic.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginRespVO {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户登录token
     */
    private String token;
}
