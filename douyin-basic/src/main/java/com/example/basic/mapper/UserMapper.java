package com.example.basic.mapper;

import com.example.basic.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author carey
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-11-25 22:28:34
* @Entity com.example.basic.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




