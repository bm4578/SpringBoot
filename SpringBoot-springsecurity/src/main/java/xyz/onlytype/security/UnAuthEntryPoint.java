package xyz.onlytype.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import xyz.onlytype.security.config.utils.ResponseUtils;
import xyz.onlytype.security.config.utils.ResultModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 白也
 * @title 无权限时处理方案
 * @date 2023/1/27 6:05 下午
 */
public class UnAuthEntryPoint implements AuthenticationEntryPoint {
    /**
     * 权限执行失败
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //返回信息
        ResponseUtils.out(response, ResultModel.error());
    }
}
