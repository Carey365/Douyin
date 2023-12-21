package com.example.basic.bo.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * commentReqBO
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class VideoReqBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 视频id 自动生成
     */
    private Long videoId;

    /**
     * 作者id 创建时token携带
     */
    private Long authorId;
}
