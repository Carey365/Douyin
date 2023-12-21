package com.example.basic.bo.video;

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
public class VideoRespBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 视频id 自动生成
     */
    private Long videoId;

    /**
     * 作者id 创建时token携带
     */
    private Long authorId;

    /**
     * 视频标题 创建时携带
     */
    private String title;

    /**
     * 视频地址 必须在创建时一同上传？这俩接口没给
     */
    private String playUrl;

    /**
     * 封面地址 必须在创建时一同上传？这俩接口没给请求路径
     */
    private String coverUrl;

    /**
     * 获赞总数
     */
    private Integer favoriteCount;

    /**
     * 评论总数
     */
    private Integer commentCount;

    /**
     * 创建时间
     */
    private Date createdTime;
}
