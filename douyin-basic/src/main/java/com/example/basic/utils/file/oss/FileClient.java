package com.example.basic.utils.file.oss;


import java.io.File;
import java.io.InputStream;


public interface FileClient {

    /**
     * 功能描述:bucket是否存在 不存在则创建
     *
     * @Param: [varl]
     * @Return: java.lang.Boolean
     **/

    Boolean checkCreateBucket(String varl);

    /**
     * 上传文件，填充file字段
     *
     * @param fileInfo
     * @return
     */
    FileInfo upload(FileInfo<File> fileInfo);

    /**
     * 上传文件，填充file字段
     *
     * @param fileInfo
     * @return
     */
    FileInfo uploadInputStream(FileInfo<InputStream> fileInfo);

    /**
     * 上传文件，填充file字段
     *
     * @param fileInfo
     * @return
     */
    FileInfo uploadLocalFile(FileInfo<File> fileInfo);

    /**
     * 新增一个上传文件接口
     *
     * @param file       要上传的文件,不可为空
     * @param bucketName 上传到哪一个bucket,不可为空
     * @param dir        目录,可以为空
     * @param fileName   文件名, 不可为空
     * @return
     */
    FileInfo upload(File file, String bucketName, String dir, String fileName);

    /**
     * 新增一个上传文件接口
     *
     * @param inputStream 输入流,不可为空
     * @param bucketName  上传到哪一个bucket,不可为空
     * @param dir         目录,可以为空
     * @param fileName    文件名, 不可为空
     * @return
     */
    FileInfo upload(InputStream inputStream, String bucketName, String dir, String fileName);

    /**
     * 新增一个上传文件接口
     *
     * @param file     要上传的文件,不可为空
     * @param dir      目录,可以为空
     * @param fileName 文件名,不可为空
     * @return
     */
    FileInfo upload(File file, String dir, String fileName);

    /**
     * 新增一个上传文件接口
     *
     * @param inputStream 输入流,不可为空
     * @param dir         目录,可以为空
     * @param fileName    文件名,不可为空
     * @return
     */
    FileInfo upload(InputStream inputStream, String dir, String fileName);


    FileInfo upload(FileInfo<File> fileInfo, FileTransferProgress progress);

    /**
     * 下载文件
     *
     * @param fileInfo
     * @return
     */
    byte[] download(FileInfo fileInfo);

    /**
     * 下载文件，把文件内容转化成base64
     *
     * @param fileInfo
     * @return
     */
    String downloadBase64(FileInfo fileInfo);


    byte[] download(FileInfo fileInfo, FileTransferProgress progress);

    boolean delete(FileInfo fileInfo);

    String getDownloadUrl(FileInfo fileInfo);

    /**
     * 获得下载地址
     *
     * @param bucketName  桶, 可以为空，为空时使用应用中默认的桶
     * @param path        下载路径
     * @param millisecond 单位毫秒, 可以为空,默认两小时7200*1000
     * @return
     */
    String getDownloadUrl(String bucketName, String path, Long millisecond);

    Boolean copyFile(FileInfo var1, FileInfo fileInfo);

    String getFileETag(String bucketName, String objectName);

    String getUploadUrl(FileInfo fileInfo);

    boolean doesObjectExist(String bucketName, String objectName);

}
