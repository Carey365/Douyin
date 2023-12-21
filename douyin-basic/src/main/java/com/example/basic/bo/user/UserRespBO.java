package com.example.basic.bo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * commentRespBO
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class UserRespBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id 自动生成
     */
    private Long userId;

    /**
     * 用户名 随用户注册上传
     */
    private String name;
    /**
     * 关注总数
     */
    private Integer followCount;

    /**
     * 粉丝总数
     */
    private Integer followerCount;

    /**
     * 作品总数
     */
    private Integer workCount;

    /**
     * 获赞总数
     */
    private Integer favoriteCount;
}
