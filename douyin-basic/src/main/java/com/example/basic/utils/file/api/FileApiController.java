package com.example.basic.utils.file.api;
import com.alibaba.fastjson.JSON;
import com.example.basic.utils.file.file.FileApi;
import com.example.basic.utils.file.file.dto.FileInfoDTO;
import com.example.basic.utils.file.oss.FileInfo;
import com.example.basic.utils.file.service.FileUploadService;
import com.example.basic.utils.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
/**
 * 文件API控制器
 * @author zhanghui708
 * @date 2023/08/21
 */
//@Service
@Slf4j
public class FileApiController implements FileApi {
    /**
     * 文件上传服务
     */
    @Autowired
    private FileUploadService fileUploadService;
    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @return {@link ServiceResponse }<{@link String }>
     */
    @Override
    public ServiceResponse<String> fileUpload(MultipartFile multipartFile){
        log.info("fileUpload接口的文件名:{}",multipartFile.getOriginalFilename());
        File file = new File("/export/upload/"+multipartFile.getOriginalFilename());
        try {
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),file);
            FileInfo<File> fileFileInfo = new FileInfo<>();
            fileFileInfo.setFile(file);
            fileFileInfo.setFileName(file.getName());
            log.info("文件上传发送参数：{}", JSON.toJSONString(fileFileInfo));
            FileInfo upload = fileUploadService.upload(fileFileInfo);
            log.info("文件上传接受参数：{}",JSON.toJSONString(upload));
            if(StringUtils.isEmpty(upload.getFileCode())){
                ServiceResponse<String> response=new ServiceResponse();
                response.fail();
                response.setData("文件上传失败");
                return response;
            }
            ServiceResponse response=new ServiceResponse();
            response.success();
            response.setData(upload.getFileCode());
            return response;
        }catch (IOException e){
            log.error("文件上传异常",e);
            ServiceResponse<String> response=new ServiceResponse();
            response.fail();
            response.setData("文件上传失败");
            return response;
        }finally {
            file.delete();
        }
    }

    /**
     * 文件下载
     *
     * @param fileInfoDTO 文件信息DTO
     * @return {@link ServiceResponse }<{@link byte[] }>
     */
    @Override
    public ServiceResponse<byte[]> download(FileInfoDTO fileInfoDTO) {
        try {
            FileInfo fileInfo = new FileInfo<>();
            fileInfo.setFileCode(fileInfoDTO.getFileKey());
            ServiceResponse<byte[]> response=new ServiceResponse();
            response.success();
            response.setData(fileUploadService.download(fileInfo));
            return response;
        }catch (Exception e){
            log.error("下载文件发生异常:{}",e);
            ServiceResponse response=new ServiceResponse();
            response.fail();
            return response;
        }
    }
}
