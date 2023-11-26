package com.example.basic.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户查询入参数
 * @author carey
 */
@Data
public class UserReqVO {
    /**
     * 用户id 自动生成
     */
    @NotNull
    private Long userId;

    /**
     * 用户名 随用户注册上传
     */
    @NotBlank
    private String name;
}
