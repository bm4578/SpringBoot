package xyz.onlytype.security.Filter;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import xyz.onlytype.config.utils.ResponseUtils;
import xyz.onlytype.config.utils.ResultModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 白也
 * @title
 * @date 2023/1/31 11:41 上午
 */
public class LoginFailureFilter implements AuthenticationFailureHandler {
    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String msg = null;
        int code = 500;
        if(exception instanceof AccountExpiredException){
            msg = "账户过期，登录失败!";
        }else if(exception instanceof BadCredentialsException){
            msg = "用户名或密码错误，登录失败!";
        } else if (exception instanceof UsernameNotFoundException) {
            msg = "用户名不存在";
        }else if(exception instanceof CredentialsExpiredException){
            msg = "密码过期，登录失败!";
        }else if(exception instanceof DisabledException){
            msg = "账户被禁用，登录失败!";
        }else if(exception instanceof LockedException){
            msg = "账户被锁，登录失败!";
        }else if(exception instanceof InternalAuthenticationServiceException){
            msg = "账户不存在，登录失败!";
        }else {
            msg = exception.getMessage();
        }
        //返回信息
        ResponseUtils.out(response, ResultModel.error(code,msg));
    }
}
