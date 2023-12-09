package com.example.basic.utils.file.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * 文件信息VO
 *
 * @author chenmengfei
 * @date 2023-1-31 10:43:20
 */
@Data
public class FileInfoVO {
    /**
     * 文件在存储系统中唯一标识
     */
    @NotBlank(message = "文件key不能为空！")
    @Size(max = 64)
    private String fileKey;

}