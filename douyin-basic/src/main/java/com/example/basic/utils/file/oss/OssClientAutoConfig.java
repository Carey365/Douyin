package com.example.basic.utils.file.oss;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: oss
 * @author yyh-kdh
 * @date 2022/12/19 15:55
 * @version 1.0
 */
//@Configuration
//@EnableConfigurationProperties(OssConf.class)
//@ConditionalOnProperty(prefix = "oss-conf", name = {"endpoint", "accessKey", "secretKey"}, matchIfMissing = false)
public class OssClientAutoConfig {

    @Autowired
    private OssConf ossConf;

    @Bean(name = "ossFileClient")
    public OssFileClient ossFileClient() {
        return new OssFileClient(ossConf);
    }
}
