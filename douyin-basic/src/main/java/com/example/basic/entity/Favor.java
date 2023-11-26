package com.example.basic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 点赞关系表
 * @TableName favor
 */
@TableName(value ="favor")
@Data
public class Favor implements Serializable {
    /**
     * 点赞id 自动生成
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 视频id
     */
    @TableField(value = "video_id")
    private Long videoId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}