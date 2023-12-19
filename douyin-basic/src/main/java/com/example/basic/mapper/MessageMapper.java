package com.example.basic.mapper;

import com.example.basic.po.MessagePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author carey
* @description 针对表【message(消息表)】的数据库操作Mapper
* @createDate 2023-11-25 22:30:50
* @Entity com.example.basic.entity.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<MessagePO> {

}




