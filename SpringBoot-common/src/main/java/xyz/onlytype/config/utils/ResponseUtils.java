package xyz.onlytype.config.utils;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * @author 白也
 * @title
 * @date 2023/1/27 6:24 下午
 */
public class ResponseUtils {
    public static void out(HttpServletResponse response, ResultModel resultModel){
        ObjectMapper objectMapper = new ObjectMapper();
        //封装response的状态码和内容格式
        response.setStatus(HttpStatus.OK.value());
        //返回格式
        response.setContentType("application/json;charset=UTF-8");
        //返回内容
        try {
            objectMapper.writeValue(response.getOutputStream(),resultModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
