package com.example.basic.manager;

import com.example.basic.bo.CommentPageReqBO;
import com.example.basic.bo.CommentPageRespBO;
import com.example.basic.bo.CommentReqBO;
import com.example.basic.bo.CommentRespBO;
import com.example.basic.po.CommentPO;

import java.util.List;
/**
 *  commentManager
 * @author chenlianghao
 * @date 2023-12-19
 */
public interface CommentManager {

    /**
     * 新增comment
     *
     * @param commentPO 请求入参
     * @return {@link Boolean}
     */
    boolean save(CommentPO commentPO);

    /**
     * 更新comment
     *
     * @param commentReqBO 请求入参
     * @return {@link Boolean}
     */
    boolean update(CommentReqBO commentReqBO);

    /**
     * 查询comment列表
     *
     * @param commentReqBO 请求入参
     * @return {@link List}<{@link CommentRespBO}>
     */
    List<CommentRespBO> queryList(CommentReqBO commentReqBO);

    /**
     * 分页查询comment列表
     *
     * @param commentPageReqBO 请求入参
     * @return {@link CommentPageRespBO }
     */
    CommentPageRespBO pageList(CommentPageReqBO commentPageReqBO);

}
