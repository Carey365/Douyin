package com.example.basic.vo;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
/**
 * commentPage分页查询出参
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Data
public class CommentPageRespVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *分页参数
     */
    private PageParam pageParam;
    /**
     * 明细列表
     */
    private List<CommentRespVO> list;
}
