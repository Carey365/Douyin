package com.example.basic.mapper;

import com.example.basic.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author carey
* @description 针对表【follow(关注表)】的数据库操作Mapper
* @createDate 2023-11-25 22:30:33
* @Entity com.example.basic.entity.Follow
*/
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {

}




