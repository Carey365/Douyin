package com.example.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.entity.Comment;
import com.example.basic.service.CommentService;
import com.example.basic.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author carey
* @description 针对表【comment(评论表)】的数据库操作Service实现
* @createDate 2023-11-25 22:30:19
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




