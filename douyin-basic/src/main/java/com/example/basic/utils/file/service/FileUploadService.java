package com.example.basic.utils.file.service;
import com.alibaba.fastjson.JSON;
import com.example.basic.utils.file.oss.FileInfo;
import com.example.basic.utils.file.oss.OssFileClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
/**
 * 图片上传service
 */
@Slf4j
//@Service
public class FileUploadService {

    @Autowired
    private OssFileClient ossFileClient;

    /**
     * 上传文件，填充file字段
     * @param fileInfo
     * @return
     */
    public FileInfo upload(FileInfo<File> fileInfo){
        FileInfo upload = null;
        try {
            upload = ossFileClient.upload(fileInfo);
        } catch (Exception e) {
            log.error("上传文件发生异常:{}", e);
        }
        return upload;
    }


    /**
     * 下载文件
     * @param fileInfo
     * @return
     */
    public byte[] download(FileInfo fileInfo){
        try {
            return ossFileClient.download(fileInfo);
        } catch (Exception e) {
            log.error("下载文件发生异常:{}", e);
        }
        return null;
    }


    /**
     * 更据key获取外链
     * @return
     */
    public String getExternalUrl(String key){
        if (StringUtils.isEmpty(key)){
            log.info("缺少文件key");
        }
        try {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileCode(key);
            log.info("获取oss外链发送参数:{}", JSON.toJSONString(fileInfo));
            String downloadUrl = ossFileClient.getDownloadUrl(fileInfo);
            log.info("获取oss外链接收参数:{}", JSON.toJSONString(downloadUrl));
            return downloadUrl;
        } catch (Exception e) {
            log.error("文件获取外链发生异常:{}", e);
        }
        return null;
    }

}