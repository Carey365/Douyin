package com.example.basic.service;

import com.example.basic.po.CommentPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.basic.vo.CommentPageReqVO;
import com.example.basic.vo.CommentPageRespVO;
import com.example.basic.vo.CommentReqVO;
import com.example.basic.vo.CommentRespVO;

import java.util.List;

/**
* @author carey
* @description 针对表【comment(评论表)】的数据库操作Service
* @createDate 2023-11-25 22:30:19
*/
public interface CommentService extends IService<CommentPO> {

    List<CommentRespVO> queryList(CommentReqVO commentReqVO);

    CommentPageRespVO pageList(CommentPageReqVO commentPageReqVO);
}
