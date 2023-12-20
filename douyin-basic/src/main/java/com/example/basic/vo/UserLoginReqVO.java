package com.example.basic.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginReqVO {
    /**
     * 用户名
     */
    @NotNull
    private String userName;

    /**
     * 密码
     */
    @NotNull
    private String passWord;
}
