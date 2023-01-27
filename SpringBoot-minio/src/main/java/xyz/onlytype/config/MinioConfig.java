package xyz.onlytype.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.onlytype.Application;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author 白也
 * @date 2022/12/21 2:15 下午
 * @title
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfig{
    private String ip;
    private String username;
    private String password;

    /**
     * 初始化连接信息
     */
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(ip)
                .credentials(username, password)
                .build();
    }

}
