package com.example.basic.mapper;

import com.example.basic.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author carey
* @description 针对表【comment(评论表)】的数据库操作Mapper
* @createDate 2023-11-25 22:30:19
* @Entity com.example.basic.entity.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




