package com.example.basic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.basic.bo.comment.CommentReqBO;
import com.example.basic.bo.comment.CommentRespBO;
import com.example.basic.po.CommentPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * commentMapper
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentPO> {

    /**
     * 更新
     *
     * @param commentReqBO 入参
     * @return int
     */
    int update(CommentReqBO commentReqBO);

    /**
     * 查询列表
     *
     * @param commentReqBO 入参
     * @return {@link List }<{@link CommentRespBO }>
     */
    List<CommentRespBO> queryList(CommentReqBO commentReqBO);

    /**
     * 分页查询列表
     *
     * @param page          分页
     * @param commentReqBO 入参
     * @return {@link IPage }<{@link CommentRespBO }>
     */
    IPage<CommentRespBO> selectPageList(IPage page, @Param("req")CommentReqBO commentReqBO);
}




