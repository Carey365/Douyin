package com.example.basic.service;

import com.example.basic.po.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.basic.vo.UserLoginReqVO;

/**
* @author carey
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-11-25 22:28:34
*/
public interface UserService extends IService<UserPO> {

    UserPO Login(UserLoginReqVO userLoginReqVO);
}
