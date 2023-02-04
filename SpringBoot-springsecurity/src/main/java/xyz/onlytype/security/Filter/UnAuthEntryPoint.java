package xyz.onlytype.security.Filter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import xyz.onlytype.config.utils.ResponseUtils;
import xyz.onlytype.config.utils.ResultModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        //返回信息
        ResponseUtils.out(response, ResultModel.error(601,"暂无权限 ! ! !"));
    }
}
