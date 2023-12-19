package com.example.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.basic.po.VideoPO;
import com.example.basic.service.VideoService;
import com.example.basic.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author carey
* @description 针对表【video(视频表)】的数据库操作Service实现
* @createDate 2023-11-25 22:30:07
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, VideoPO>
    implements VideoService{

}




