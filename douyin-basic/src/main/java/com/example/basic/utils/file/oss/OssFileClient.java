package com.example.basic.utils.file.oss;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.event.ProgressEventType;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @description: oss配置
 * @author yyh-kdh
 * @date 2022/12/19 16:44
 * @version 1.0
 */
public class OssFileClient implements FileClient {

    private static final Logger log = LoggerFactory.getLogger(OssFileClient.class);

    /**
     * 默认的下载url有效期默认两小时
     */
    public static final long DEFAULT_DOWNLOAD_URL_EXP_MS = 1000 * 60 * 60 * 2L;

    /**
     * 上传文件类型
     */
    private static final List<String> SUPPORTED_SUFFIX_LIST = Arrays.asList(".xls", ".xlsx", ".doc", ".docx", ".pdf", ".jpg", ".jpeg", ".bmp", ".png", ".zip", ".rar", ".7z");
    /**
     * 给风控的下载url有效期
     */
    public static final long RISK_DOWNLOAD_URL_EXP_MS = 1000L * 60 * 60 * 24 * 365 * 10;

    public static final long EXP_YEAR = 1000L * 60 * 60 * 24 * 365 ;

    public static final long EXP_DAY = 1000L * 60 * 60 * 24;

    public String endPoint;

    public String accessKey;

    public String secretKey;

    public String region;

    public String bucketName;

    public String protocol;

    private AmazonS3 amazonS3Client;

    public OssFileClient(OssConf ossConf) {
        endPoint = ossConf.getEndPoint();
        accessKey = ossConf.getAccessKey();
        secretKey = ossConf.getSecretKey();
        region = ossConf.getRegion();
        bucketName = ossConf.getBucketName();
        protocol = ossConf.getProtocol();
        init();
    }

    /**
     * 初始化buket
     */
    private void init() {
        Protocol webProtocol = Protocol.HTTP;
        if (StringUtils.isBlank(protocol)){
            throw new RuntimeException("初始化OSS客户端,http协议必须指定,http或https");
        }else if (protocol.equalsIgnoreCase("http")){
            webProtocol = Protocol.HTTP;
        } else if (protocol.equalsIgnoreCase("https")){
            webProtocol = Protocol.HTTPS;
        }
        amazonS3Client = AmazonS3Client.builder()
                .withCredentials((new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(new ClientConfiguration().withProtocol(webProtocol)
                        .withSignerOverride("S3SignerType")) //使用V2签名
                .build();
        log.info("build AmazonS3Client with V2 ok! endPoint:{} region:{}", endPoint, region);
    }

    /**
     * 检查创建bucket, 目前采用一个桶，通过管理界面创建
     *
     * @param bucketName
     */
    @Override
    public Boolean checkCreateBucket(String bucketName) {
        if (bucketName == null || bucketName.isEmpty()) {
            log.info("checkCreateBucket : empty bucketName!");
            return false;
        }
        if (amazonS3Client.doesBucketExistV2(bucketName)) {
            log.info("bucketName already exist. {}", bucketName);
        } else {
            Bucket bucket = amazonS3Client.createBucket(bucketName);
            return bucket != null;
        }
        return false;
    }

    @Override
    public FileInfo upload(FileInfo<File> fileInfo) {
        return upload(fileInfo, null);
    }

    /**
     * 文件上传 - MultipartFile
     *
     * @param fileInfo
     * @param progress
     * @return
     */
    @Override
    public FileInfo upload(FileInfo<File> fileInfo, FileTransferProgress progress) {
        File file = null;
        if (fileInfo == null || (file = fileInfo.getFile()) == null) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            log.info("打开文件输入流异常", e);
            return null;
        }
        return uploadInner(fileInfo, inputStream, progress);
    }

    /**
     * 文件上传 - 输入流
     *
     * @param fileInfo
     * @return
     */
    @Override
    public FileInfo uploadInputStream(FileInfo<InputStream> fileInfo) {
        InputStream inputStream = null;
        if (fileInfo == null || (inputStream = fileInfo.getFile()) == null) {
            throw new RuntimeException("文件上传内容为空");
        }
        return uploadInner(fileInfo, inputStream, null);
    }

    /**
     * 上传本地文件
     *
     * @param fileInfo
     * @return
     */
    @Override
    public FileInfo uploadLocalFile(FileInfo<File> fileInfo) {
        File file = null;
        if (fileInfo == null || (file = fileInfo.getFile()) == null) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            log.info("打开文件输入流异常", e);
            return null;
        }
        return uploadInner(fileInfo, inputStream, null);
    }

    @Override
    public FileInfo upload(File file, String bucketName, String dir, String fileName) {
        if (file == null || StringUtils.isBlank(bucketName) || StringUtils.isBlank(fileName)) {
            throw new RuntimeException("com.jd.jdt.std.oss.OssFileClient upload方法: file、bucketName、fileName三个参数不允许为空");
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            log.info("打开文件输入流异常", e);
            throw new RuntimeException("打开文件输入流异常", e);
        }
        return upload(inputStream, bucketName, dir, fileName);
    }

    @Override
    public FileInfo upload(InputStream inputStream, String bucketName, String dir, String fileName) {
        try {
            String fileKey = this.buildFileVirtualKey(dir, fileName);
            if (!bucketName.equals(this.bucketName)) {
                checkCreateBucket(bucketName);
            }
            PutObjectResult putObjectResult = amazonS3Client.putObject(bucketName, fileKey, inputStream, new ObjectMetadata());
            FileInfo fileInfoRet = new FileInfo();
            fileInfoRet.setFileCode(fileKey);
            fileInfoRet.setMd5(putObjectResult.getContentMd5());
            return fileInfoRet;
        } catch (Exception e) {
            log.error("ossFileClient上传文件异常! endPoint:" + endPoint + " accessKey:" +accessKey + " secretKey:" + secretKey + "bucketName:" + bucketName  , e);
            throw new RuntimeException("ossFileClient upload上传文件异常", e);
        }
    }

    @Override
    public FileInfo upload(File file, String dir, String fileName) {
        return upload(file, this.bucketName, dir, fileName);
    }

    @Override
    public FileInfo upload(InputStream inputStream, String dir, String fileName) {
        return upload(inputStream, this.bucketName, dir, fileName);
    }

    private String buildFileVirtualKey(String dir, String fileName) {
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("文件名称为空");
        }
        if (StringUtils.isBlank(dir)) {
            return fileName;
        } else {
            String virtualKey = dir + '/' + fileName;
            virtualKey = StringUtils.replace(virtualKey, "\\", "/");
            virtualKey = StringUtils.replace(virtualKey, "//", "/");
            return virtualKey;
        }
    }

    /**
     * 文件上传-文件流
     *
     * @param fileInfo
     * @param inputStream 文件输入流
     * @param progress
     * @return
     */
    private FileInfo uploadInner(FileInfo fileInfo, InputStream inputStream, FileTransferProgress progress) {
        PutObjectResult putObjectResult = null;
        String objectName = null; //文件的key
        String tmpBucketName = this.bucketName;
        //设置传输类型
        Boolean check = checkSuffix(fileInfo.getFileName());
        if (check){
            log.info("上传文件类型不支持");
        }
        if (!StringUtils.isBlank(fileInfo.getFileCode())) {
            objectName = fileInfo.getFileCode()+"."+fileInfo.getFileName().substring(fileInfo.getFileName().lastIndexOf(".")+1); //外部指定了文件的key则直接使用
        } else {
            objectName = UUID.randomUUID().toString()+"."+fileInfo.getFileName().substring(fileInfo.getFileName().lastIndexOf(".")+1);
        }
        //bucketName 非空 使用外部bucketName
        if (StringUtils.isNotBlank(fileInfo.getBucketName())) {
            tmpBucketName = fileInfo.getBucketName();
        }
        try {
            if (progress != null) {
                putObjectResult = amazonS3Client.putObject(new PutObjectRequest(tmpBucketName, objectName,
                        inputStream, new ObjectMetadata()).withGeneralProgressListener(new ProgressListener(progress)));
            } else {
                putObjectResult = amazonS3Client.putObject(tmpBucketName, objectName, inputStream, new ObjectMetadata());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("upload fail! endPoint:" + endPoint + " accessKey:" +accessKey + " secretKey:" + secretKey + "bucketName:" + tmpBucketName  , e);
        }
        if (putObjectResult != null && !StringUtils.isEmpty(putObjectResult.getContentMd5())) {
            FileInfo fileInfoRet = new FileInfo();
            fileInfoRet.setFileCode(objectName);
            fileInfoRet.setMd5(putObjectResult.getContentMd5());
            return fileInfoRet;
        }
        return new FileInfo();
    }

    @Override
    public byte[] download(FileInfo fileInfo) {
        return download(fileInfo, null);
    }

    @Override
    public String downloadBase64(FileInfo fileInfo) {
        byte[] data = download(fileInfo, null);
        if (data != null && data.length > 0) {
            //文件字节数组转base64
            return new BASE64Encoder().encode(data).replace("\n", "").replace("\r", "");
        } else {
            return null;
        }
    }

    @Override
    public byte[] download(FileInfo fileInfo, FileTransferProgress progress) {
        String objectName = fileInfo.getFileCode();
        if (StringUtils.isEmpty(objectName)) {
            log.info("download fail! objectName empty");
            return new byte[0];
        }
        S3Object s3Object = null;
        if (progress != null) {
            s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, objectName)
                    .withGeneralProgressListener(new ProgressListener(progress)));
        } else {
            s3Object = amazonS3Client.getObject(bucketName, objectName);
        }
        if (s3Object == null) {
            log.info("download fail! getObject empty");
            return new byte[0];
        }
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while (-1 != (n = s3ObjectInputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } catch (IOException e) {
            log.info("download fail!", e);
        }
        return new byte[0];
    }

    @Override
    public boolean delete(FileInfo var1) {
        return false;
    }

    /**
     * 获取签名的且有失效性的预览/下载url
     *
     * @param fileInfo
     * @return
     */
    @Override
    public String getDownloadUrl(FileInfo fileInfo) {
        String objectName = fileInfo.getFileCode();
        long expiredMs = 7200L * 1000;
        if (fileInfo.getUrlExpiredMs() > 0) {
            expiredMs = fileInfo.getUrlExpiredMs();
        }
        //未传bucketName 则取默认 否则传请求传递的bucketName
        String bucketNameReq = StringUtils.isBlank(fileInfo.getBucketName()) ? bucketName : fileInfo.getBucketName();

        boolean fileExist = amazonS3Client.doesObjectExist(bucketNameReq,objectName);
        if (fileExist==false){
            return null;
        }
        URL url = amazonS3Client.generatePresignedUrl(bucketNameReq, objectName,
                getExpiredDate(expiredMs), HttpMethod.GET);
        log.info("endPoint:{},accessKey:{},secretKey:{},region:{},bucketName:{},objectName:{}, url:{}", endPoint, accessKey, secretKey, region, bucketNameReq, objectName, url.toString());
        return url != null ? url.toString() : null;
    }

    public static Date getExpiredDate(Long expiredMs) {
        long addYears = expiredMs/EXP_YEAR;
        long dayExpiredMs = expiredMs%EXP_YEAR;
        long addDays = dayExpiredMs/EXP_DAY;
        long addMs = dayExpiredMs%EXP_DAY;
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.YEAR,(int)addYears);
        cal.add(Calendar.DAY_OF_YEAR,(int)addDays);
        cal.add(Calendar.MILLISECOND,(int)addMs);
        return cal.getTime();
    }

    @Override
    public String getDownloadUrl(String bucketName, String path, Long millisecond) {
        if (StringUtils.isBlank(bucketName)) {
            bucketName = this.bucketName;
        }
        long expiredMs = 7200L * 1000;
        if (millisecond != null) {
            expiredMs = millisecond;
        }

        boolean fileExist = amazonS3Client.doesObjectExist(bucketName,path);
        if (fileExist==false){
            return null;
        }
        URL url = amazonS3Client.generatePresignedUrl(bucketName, path,
                getExpiredDate(expiredMs), HttpMethod.GET);
        log.info("endPoint:{},accessKey:{},secretKey:{},region:{},bucketName:{},objectName:{}, url:{}", endPoint, accessKey, secretKey, region, bucketName, path, url.toString());
        return url != null ? url.toString() : null;
    }

    @Override
    public Boolean copyFile(FileInfo var1, FileInfo var2) {
        if (Objects.isNull(var1) || Objects.isNull(var2)) {
            log.error("copy fail! fileInfo exists empty!");
            return false;
        }
        if (StringUtils.isAnyBlank(var1.getBucketName(), var1.getFileCode(), var2.getBucketName(), var2.getFileCode())) {
            log.error("copy fail! bucketName or objectName exists empty!");
            return false;
        }
        amazonS3Client.copyObject(var1.getBucketName(), var1.getFileCode(), var2.getBucketName(), var2.getFileCode());
        return true;
    }

    @Override
    /**
     * 功能描述: 获取文件Etag 小文件为etag = md5(小对象的数据)  大文件为etag = md5(每个分块的MD5拼成一个字符串)
     * @Param:  \
     * @Return: java.lang.String
     **/
    public String getFileETag(String bucketName, String objectName) {
        //为空取默认bucketName
        if (StringUtils.isBlank(bucketName)) {
            bucketName = this.bucketName;
        }
        ObjectMetadata metadata = amazonS3Client.getObjectMetadata(bucketName, objectName);
        return metadata.getETag();
    }

    @Override
    public String getUploadUrl(FileInfo fileInfo) {
        String objectName = fileInfo.getFileCode();
        //默认上传时间为6小时
        long expiredMs = 21600L * 1000;
        if (fileInfo.getUrlExpiredMs() > 0) {
            expiredMs = fileInfo.getUrlExpiredMs();
        }
        //未传bucketName 则取默认 否则传请求传递的bucketName
        String bucketNameReq = StringUtils.isBlank(fileInfo.getBucketName()) ? bucketName : fileInfo.getBucketName();
        URL url = amazonS3Client.generatePresignedUrl(bucketNameReq, objectName,
                getExpiredDate(expiredMs), HttpMethod.PUT);
        log.info("bucketName:{},objectName:{}, url:{}", bucketNameReq, objectName, url.toString());
        return url != null ? url.toString() : null;
    }

    @Override
    public boolean doesObjectExist(String bucketName, String objectName) {
        return amazonS3Client.doesObjectExist(bucketName, objectName);
    }

    /**
     * 通过jrss的key获取下载链接（使用默认超时时间）
     *
     * @param jrssKey jrss存储中的文件key
     * @return 下载链接
     */
    public String getDownloadUrl(String jrssKey) {
        return this.getDownloadUrl(jrssKey, DEFAULT_DOWNLOAD_URL_EXP_MS);
    }

    /**
     * 通过jrss的key获取下载链接（指定超时时间）
     *
     * @param jrssKey   jrss存储中的文件key
     * @param expiredMs 超时时间
     * @return 下载链接
     */
    public String getDownloadUrl(String jrssKey, long expiredMs) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileCode(jrssKey);
        fileInfo.setUrlExpiredMs(expiredMs);
        return this.getDownloadUrl(fileInfo);
    }

    /**
     * 下载进度监听器
     */
    private static class ProgressListener implements com.amazonaws.event.ProgressListener {
        private long bytesRead = 0;
        private long totalBytes = -1;
        private boolean succeed = false;
        private FileTransferProgress fileTransferProgress; //外部进度监听器

        /**
         * 构造器接收外部监听器
         *
         * @param progress
         */
        public ProgressListener(FileTransferProgress progress) {
            fileTransferProgress = progress;
        }

        /**
         * 通知进度
         */
        private void notifyProgress() {
            if (fileTransferProgress != null) {
                if (this.totalBytes != -1) {
                    int percent = (int) (this.bytesRead * 100.0 / this.totalBytes);
                    //log.info("transfer progress: " + percent + "%(" + this.bytesRead + "/" + this.totalBytes + ")");
                    fileTransferProgress.onProgress(bytesRead, totalBytes, percent);
                } else {
                    log.warn("transfer success: {}, transfer ratio: unknown({})", this.succeed, this.bytesRead);
                }
            }
        }

        @Override
        public void progressChanged(com.amazonaws.event.ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            if (eventType == ProgressEventType.TRANSFER_STARTED_EVENT) {
                //log.info("==>> start to transfer...");
            } else if (eventType == ProgressEventType.RESPONSE_CONTENT_LENGTH_EVENT) {
                this.totalBytes = bytes;
                notifyProgress();
                //log.info("==>> start to transfer...");
            } else if (eventType == ProgressEventType.RESPONSE_BYTE_TRANSFER_EVENT) {
                this.bytesRead += bytes;
                notifyProgress();
                //log.info("==>> transfer ing...");
            } else if (eventType == ProgressEventType.TRANSFER_COMPLETED_EVENT) {
                this.succeed = true;
                //log.info("==>> transfer completed!");
            } else if (eventType == ProgressEventType.TRANSFER_FAILED_EVENT) {
                this.succeed = false;
                log.warn("==>> transfer failed!");
            }
        }
    }


    private boolean checkSuffix(String fileName) {
        String suffixName = null;
        int lastIndex = StringUtils.lastIndexOf(fileName, ".");
        if (lastIndex != -1) {
            suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        }
        if (StringUtils.isEmpty(suffixName)){
            return Boolean.TRUE;
        }
        if (!SUPPORTED_SUFFIX_LIST.contains(suffixName)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}


