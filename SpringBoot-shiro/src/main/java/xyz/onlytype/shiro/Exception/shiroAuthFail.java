package xyz.onlytype.shiro.Exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.onlytype.util.R;


/**
 * @author 白也
 * @date 2023/1/23 21:44
 */

@ControllerAdvice
public class shiroAuthFail {
    /**
     * shiro异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public R bizExceptionHandler(AuthorizationException e){
        return R.fail("权限异常："+ e.getMessage());
    }
}
