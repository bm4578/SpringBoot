package xyz.onlytype.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
