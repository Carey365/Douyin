package com.example.basic.mapper;

import com.example.basic.po.VideoPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author carey
* @description 针对表【video(视频表)】的数据库操作Mapper
* @createDate 2023-11-25 22:30:07
* @Entity com.example.basic.entity.Video
*/
@Mapper
public interface VideoMapper extends BaseMapper<VideoPO> {

}




