package com.example.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.po.MessagePO;
import com.example.basic.service.MessageService;
import com.example.basic.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author carey
* @description 针对表【message(消息表)】的数据库操作Service实现
* @createDate 2023-11-25 22:30:50
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessagePO>
    implements MessageService{

}




