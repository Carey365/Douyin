package com.example.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.entity.Follow;
import com.example.basic.service.FollowService;
import com.example.basic.mapper.FollowMapper;
import org.springframework.stereotype.Service;

/**
* @author carey
* @description 针对表【follow(关注表)】的数据库操作Service实现
* @createDate 2023-11-25 22:30:33
*/
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow>
    implements FollowService{

}




