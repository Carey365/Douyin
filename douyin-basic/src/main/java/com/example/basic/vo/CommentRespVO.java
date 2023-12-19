package com.example.basic.vo;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
/**
 * comment响应出参
 *
 * @author chenlianghao
 * @date 2023-12-19 15:47
 */
@Data
public class CommentRespVO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;
}