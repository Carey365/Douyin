package com.example.basic.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.basic.vo.PageParam;
import lombok.Data;
/**
 * commentPage分页查询出参
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class CommentPageRespBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *分页参数
     */
    private PageParam pageParam;
    /**
     * 明细列表
     */
    private List<CommentRespBO> list;

    public CommentPageRespBO(){
        this.pageParam = new PageParam();
        this.list = new ArrayList<>();
    }
}
