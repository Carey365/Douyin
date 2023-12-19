package com.example.basic.mapper;

import com.example.basic.po.FavorPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author carey
* @description 针对表【favor(点赞关系表)】的数据库操作Mapper
* @createDate 2023-11-25 22:30:29
* @Entity com.example.basic.entity.Favor
*/
@Mapper
public interface FavorMapper extends BaseMapper<FavorPO> {

}




