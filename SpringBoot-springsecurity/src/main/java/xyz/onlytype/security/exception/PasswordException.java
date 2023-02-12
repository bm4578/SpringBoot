package xyz.onlytype.security.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author 白也
 * @title 密码错误
 * @date 2023/2/10 1:56 下午
 */
public class PasswordException extends BadCredentialsException {
    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public PasswordException(String msg) {
        super(msg);
    }
}
