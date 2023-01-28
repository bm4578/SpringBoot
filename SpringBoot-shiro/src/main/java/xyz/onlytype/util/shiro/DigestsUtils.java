package xyz.onlytype.util.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author 白也
 * @title 生成摘要
 * @date 2023/1/26 10:01
 */
public class DigestsUtils {
    public static String sha1(String password,String salt){
        return new SimpleHash("",password,salt,1234).toString();
    }

}