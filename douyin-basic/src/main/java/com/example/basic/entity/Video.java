package com.example.basic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 视频表
 * @TableName video
 */
@TableName(value ="video")
@Data
public class Video implements Serializable {
    /**
     * 视频id 自动生成
     */
    @TableId(value = "video_id", type = IdType.AUTO)
    private Long videoId;

    /**
     * 作者id 创建时token携带
     */
    @TableField(value = "author_id")
    private Long authorId;

    /**
     * 视频标题 创建时携带
     */
    @TableField(value = "title")
    private String title;

    /**
     * 视频地址 必须在创建时一同上传？这俩接口没给
     */
    @TableField(value = "play_url")
    private String playUrl;

    /**
     * 封面地址 必须在创建时一同上传？这俩接口没给请求路径
     */
    @TableField(value = "cover_url")
    private String coverUrl;

    /**
     * 获赞总数
     */
    @TableField(value = "favorite_count")
    private Integer favoriteCount;

    /**
     * 评论总数
     */
    @TableField(value = "comment_count")
    private Integer commentCount;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}