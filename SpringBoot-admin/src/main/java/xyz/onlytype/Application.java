package xyz.onlytype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import xyz.onlytype.config.web.InitProject;

/**
 * @author 白也
 * @title 项目启动类
 * @date 2023/2/4 9:40 下午
 */
@SpringBootApplication
@EnableConfigurationProperties({InitProject.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
