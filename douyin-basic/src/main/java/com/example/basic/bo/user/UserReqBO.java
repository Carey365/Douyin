package com.example.basic.bo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * commentReqBO
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class UserReqBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String name;
}
