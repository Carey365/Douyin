package com.example.basic.bo.video;

import com.example.basic.vo.PageParam;
import lombok.Data;

import java.io.Serializable;

/**
 * commentPageReqBO
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class VideoPageReqBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *分页参数
     */
    private PageParam pageParam;
    /**
     * 视频id 自动生成
     */
    private Long videoId;

    /**
     * 作者id 创建时token携带
     */
    private Long authorId;
}