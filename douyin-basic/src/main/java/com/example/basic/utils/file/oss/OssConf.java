package com.example.basic.utils.file.oss;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: oss配置
 * @author yyh-kdh
 * @date 2022/12/19 16:44
 * @version 1.0
 */
//@ConfigurationProperties(prefix = "oss-conf")
public class OssConf {

    public String endPoint;

    public String accessKey;

    public String secretKey;

    public String region;

    public String bucketName;

    public String protocol;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
