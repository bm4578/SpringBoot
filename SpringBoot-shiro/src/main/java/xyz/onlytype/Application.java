package xyz.onlytype;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import xyz.onlytype.web.InitProject;

/**
 * @author 白也
 * @date 2023/01/08 2:12 下午
 * @title
 */
@SpringBootApplication
@MapperScan("xyz.onlytype.dao")
@EnableConfigurationProperties({InitProject.class})
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
