package com.example.basic.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserPO implements Serializable {
    /**
     * 用户id 自动生成
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名 随用户注册上传
     */
    @TableField(value = "name")
    private String name;

    /**
     * 密码 通过md5加密后存储
     */
    @TableField(value = "password")
    private String password;

    /**
     * 关注总数
     */
    @TableField(value = "follow_count")
    private Integer followCount;

    /**
     * 粉丝总数
     */
    @TableField(value = "follower_count")
    private Integer followerCount;

    /**
     * 作品总数
     */
    @TableField(value = "work_count")
    private Integer workCount;

    /**
     * 获赞总数
     */
    @TableField(value = "favorite_count")
    private Integer favoriteCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}