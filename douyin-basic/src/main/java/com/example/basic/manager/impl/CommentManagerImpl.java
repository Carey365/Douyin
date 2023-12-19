package com.example.basic.manager.impl;

import com.example.basic.bo.CommentPageReqBO;
import com.example.basic.bo.CommentPageRespBO;
import com.example.basic.bo.CommentReqBO;
import com.example.basic.bo.CommentRespBO;
import com.example.basic.converter.CommentConverter;
import com.example.basic.manager.CommentManager;
import com.example.basic.mapper.CommentMapper;
import com.example.basic.po.CommentPO;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import java.util.List;

/**
 *  commentManagerImpl
 * @author chenlianghao
 * @date 2023-12-19
 */
@Component
public class CommentManagerImpl implements CommentManager {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 新增comment
     *
     * @param commentPO 请求入参
     * @return {@link boolean}
     */
    @Override
    public boolean save(CommentPO commentPO) {
        return SqlHelper.retBool(commentMapper.insert(commentPO));
    }

    /**
     * 更新comment
     *
     * @param commentReqBO 请求入参
     * @return {@link boolean}
     */
    @Override
    public boolean update(CommentReqBO commentReqBO) {
        return SqlHelper.retBool(commentMapper.update(commentReqBO));
    }

    /**
     * 查询comment列表
     *
     * @param commentReqBO 请求入参
     * @return {@link List}<{@link CommentRespBO}>
     */
    @Override
    public List<CommentRespBO> queryList(CommentReqBO commentReqBO){
        return commentMapper.queryList(commentReqBO);
    }

    /**
     * 分页查询comment列表
     *
     * @param commentPageReqBO 请求入参
     * @return {@link CommentPageRespBO }
     */
    @Override
    public CommentPageRespBO pageList(CommentPageReqBO commentPageReqBO){
        CommentPageRespBO commentPageRespBO = new CommentPageRespBO();
        Page page = new Page(commentPageReqBO.getPageParam().getPageNum(), commentPageReqBO.getPageParam().getPageSize());
        CommentReqBO commentReqBO = CommentConverter.INSTANCE.pageConvert(commentPageReqBO);
        IPage<CommentRespBO> respBOIPage = commentMapper.selectPageList(page,commentReqBO);
        commentPageRespBO.setList(respBOIPage.getRecords());
        commentPageRespBO.getPageParam().setPages((int)respBOIPage.getPages());
        commentPageRespBO.getPageParam().setPageNum((int)respBOIPage.getCurrent());
        commentPageRespBO.getPageParam().setPageSize((int)respBOIPage.getSize());
        commentPageRespBO.getPageParam().setTotal((int)respBOIPage.getTotal());
        return commentPageRespBO;
    }

}
