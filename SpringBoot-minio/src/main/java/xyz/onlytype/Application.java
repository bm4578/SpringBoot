package xyz.onlytype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import xyz.onlytype.security.config.MinioConfig;
import xyz.onlytype.security.config.web.InitProject;

/**
 * @author 白也
 * @date 2022/12/21 2:12 下午
 * @title
 */
@SpringBootApplication
@EnableConfigurationProperties({MinioConfig.class, InitProject.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
