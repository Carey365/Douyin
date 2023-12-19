package com.example.basic.vo;
import java.util.Date;
import lombok.Data;
import javax.validation.constraints.NotNull;
/**
 * commentPage分页查询入参
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class CommentPageReqVO {

    private static final long serialVersionUID = 1L;

    /**
     *分页参数
     */
    @NotNull
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
