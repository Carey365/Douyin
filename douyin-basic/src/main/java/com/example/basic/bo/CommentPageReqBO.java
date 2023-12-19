package com.example.basic.bo;

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
public class CommentPageReqBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *分页参数
     */
    private PageParam pageParam;
    /**
     * 视频id
     */
    private Long videoId;
    /**
     * 评论者id
     */
    private Long authorId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createdTime;
}