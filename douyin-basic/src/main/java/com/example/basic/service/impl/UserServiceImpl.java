package com.example.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.entity.User;
import com.example.basic.eums.ResponsEums;
import com.example.basic.service.UserService;
import com.example.basic.mapper.UserMapper;
import com.example.basic.utils.ServiceResponse;
import com.example.basic.vo.UserReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
* @author carey
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-11-25 22:28:34
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;

    public ServiceResponse<User> getUser(UserReqVO userReqVO) {
        User user=userMapper.selectById(userReqVO.getUserId());
        ServiceResponse<User> response=new ServiceResponse<>();
        if(!ObjectUtils.isEmpty(user)){
            response.success();
            response.setData(user);
            return response;
        }
        response.fail();
        return response;
    }
}




