package com.example.basic.utils.file.file;
import com.example.basic.utils.file.file.dto.FileInfoDTO;
import com.example.basic.utils.response.ServiceResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * 文件API
 * @author zhanghui708
 * @date 2023/08/21
 */
public interface FileApi {
    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @return {@link ServiceResponse }<{@link String }>
     */
    ServiceResponse<String> fileUpload(MultipartFile multipartFile) throws IOException;

    /**
     * 文件下载
     *
     * @param fileInfoDTO 文件信息DTO
     * @return {@link ServiceResponse }<{@link byte[] }>
     */
    ServiceResponse<byte[]> download(FileInfoDTO fileInfoDTO);
}
