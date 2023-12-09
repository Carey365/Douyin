package com.example.basic.utils.file.oss;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class FileInfo<T> {
    private T file;
    /**
     * 文件在存储系统中唯一标识
     */
    private String fileCode;
    private String filePath;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String md5;
    private String downloadUrl;
    /**
     * 请求预览文件的过期失效，如果不给定则默认2h
     */
    private long urlExpiredMs;
    /**
     * 【可选】上传到指定的bucket， 不传则是默认bucket
     */
    private String bucketName;
}