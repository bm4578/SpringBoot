package xyz.onlytype.config.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.InetAddress;

@Validated
@ConfigurationProperties(prefix = "server")
@Data
@Slf4j
public class InitProject implements ApplicationRunner{
    @Max(value = 1288,message = "端口限制，不能超过最大值")
    @Min(value = 100,message = "端口限制，不能低于最小值")
    private int port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        String url = "http://"+localHost.getCanonicalHostName()+":"+port;
        log.warn("接口地址："+url);
        log.warn("swagger文档地址:  " +url+"/doc.html");
    }
}
