package com.example.basic.bo.video;

import com.example.basic.vo.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * commentPage分页查询出参
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class VideoPageRespBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *分页参数
     */
    private PageParam pageParam;
    /**
     * 明细列表
     */
    private List<VideoRespBO> list;

    public VideoPageRespBO(){
        this.pageParam = new PageParam();
        this.list = new ArrayList<>();
    }
}
