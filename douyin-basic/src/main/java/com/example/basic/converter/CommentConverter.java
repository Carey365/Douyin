package com.example.basic.converter;

import com.example.basic.bo.comment.CommentPageReqBO;
import com.example.basic.bo.comment.CommentPageRespBO;
import com.example.basic.bo.comment.CommentReqBO;
import com.example.basic.bo.comment.CommentRespBO;
import com.example.basic.po.CommentPO;
import com.example.basic.vo.CommentPageReqVO;
import com.example.basic.vo.CommentPageRespVO;
import com.example.basic.vo.CommentReqVO;
import com.example.basic.vo.CommentRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * commentConverter转换
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@Mapper
public interface CommentConverter {

    CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

    /**
     * 转换BO
     *
     * @param commentReqVO 入参
     * @return {@link CommentReqBO }
     */
    CommentReqBO convert(CommentReqVO commentReqVO);

    /**
     * 转换PO
     *
     * @param commentReqVO 入参
     * @return {@link CommentPO }
     */
    CommentPO convertToPO(CommentReqVO commentReqVO);

    /**
     * 转换VOList
     *
     * @param commentRespBOList 入参
     * @return {@link List }<{@link CommentRespVO }>
     */
    List<CommentRespVO> convertToVOList(List<CommentRespBO> commentRespBOList);

    /**
     * 分页参数转换
     *
     * @param commentPageReqBO 入参
     * @return {@link CommentReqBO }
     */
    CommentReqBO pageConvert(CommentPageReqBO commentPageReqBO);

    /**
     * pageReqVO转换pageReqBO
     *
     * @param commentPageReqVO 入参
     * @return {@link CommentPageReqBO }
     */
    CommentPageReqBO convertReqBO(CommentPageReqVO commentPageReqVO);

    /**
     * 转换响应VO
     *
     * @param commentPageRespBO 入参
     * @return {@link CommentPageRespVO }
     */
    CommentPageRespVO convertRespVO(CommentPageRespBO commentPageRespBO);
}
