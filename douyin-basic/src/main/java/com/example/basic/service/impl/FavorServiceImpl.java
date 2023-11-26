package com.example.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.entity.Favor;
import com.example.basic.service.FavorService;
import com.example.basic.mapper.FavorMapper;
import org.springframework.stereotype.Service;

/**
* @author carey
* @description 针对表【favor(点赞关系表)】的数据库操作Service实现
* @createDate 2023-11-25 22:30:29
*/
@Service
public class FavorServiceImpl extends ServiceImpl<FavorMapper, Favor>
    implements FavorService{

}




