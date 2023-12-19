package com.example.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.po.CommentPO;
import com.example.basic.service.CommentService;
import com.example.basic.mapper.CommentMapper;
import com.example.basic.vo.CommentPageReqVO;
import com.example.basic.vo.CommentPageRespVO;
import com.example.basic.vo.CommentReqVO;
import com.example.basic.vo.CommentRespVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author carey
* @description 针对表【comment(评论表)】的数据库操作Service实现
* @createDate 2023-11-25 22:30:19
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentPO>
    implements CommentService{

    @Override
    public List<CommentRespVO> queryList(CommentReqVO commentReqVO) {
        return null;
    }

    @Override
    public CommentPageRespVO pageList(CommentPageReqVO commentPageReqVO) {
        return null;
    }
}




