package com.example.basic.controller;
import com.example.basic.converter.CommentConverter;
import com.example.basic.service.CommentService;
import com.example.basic.utils.response.PageResponseTransformer;
import com.example.basic.utils.response.PageResponse;
import com.example.basic.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

/**
 * commentFrontController
 *
 * @author chenlianghao
 * @date 2023-12-19
 */
@RestController
@RequestMapping(value = "/douyin/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 新增comment
     *
     * @param commentReqVO 请求入参
     * @return {@link PageResponse}<{@link Boolean}>
     */
    @PostMapping("/add")
    public PageResponse<Boolean> add(@RequestBody @Valid CommentReqVO commentReqVO){
        return PageResponseTransformer.buildResponseSuccess(commentService.save(CommentConverter.INSTANCE.convertToPO(commentReqVO)));
    }

    /**
     * 更新comment
     *
     * @param commentReqVO 请求入参
     * @return {@link PageResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    public PageResponse<Boolean> update(@RequestBody @Valid CommentReqVO commentReqVO){
        return PageResponseTransformer.buildResponseSuccess(commentService.updateById(CommentConverter.INSTANCE.convertToPO(commentReqVO)));
    }

    /**
     * 查询comment列表
     *
     * @param commentReqVO 请求入参
     * @return {@link PageResponse}<{@link CommentRespVO}>
     */
    @PostMapping("/list")
    public PageResponse<List<CommentRespVO>> queryList(@RequestBody @Valid CommentReqVO commentReqVO) {
        List<CommentRespVO>  commentRespVOS = commentService.queryList(commentReqVO);
        return PageResponseTransformer.buildResponseSuccess(commentRespVOS);
    }

    /**
     * 分页查询comment列表
     *
     * @param commentPageReqVO 请求入参
     * @return {@link PageResponse }<{@link CommentPageRespVO }>
     */
    @PostMapping("/pageList")
    public PageResponse<CommentPageRespVO> pageList(@RequestBody @Valid CommentPageReqVO commentPageReqVO) {
        CommentPageRespVO  commentPageRespVO = commentService.pageList(commentPageReqVO);
        return PageResponseTransformer.buildResponseSuccess(commentPageRespVO);
    }
}