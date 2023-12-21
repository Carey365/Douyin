package com.example.basic.bo.user;

import com.example.basic.vo.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * commentPageReqBO
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class UserPageReqBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *分页参数
     */
    private PageParam pageParam;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String name;
}