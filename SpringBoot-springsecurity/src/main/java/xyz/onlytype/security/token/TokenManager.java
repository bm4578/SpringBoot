package xyz.onlytype.security.token;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.onlytype.entity.SecurityUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 白也
 * @title token生成器
 * @date 2023/1/27 20:17
 */
@Component("tokenManager")
@Slf4j
public class TokenManager {
    @Value("${token.secret}")
    private String secret;
    //创建创建时间
    private static final long TIME = 1800000;
    /**
     * 生成密钥
     * @param username 非隐私信息
     */
    public  String getToken(String username) {
        //过期时间默认30分钟
        Date expirationDate = new Date(System.currentTimeMillis() + TIME);
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.ISSUER,username);
        claims.put(Claims.ISSUED_AT, new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析和校验token
     * @param token 令牌
     * @return 数据声明
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
        } catch (ClaimJwtException e) {
            log.error("error", e);
        }
        return claims;
    }
    /**
     * 获取用户名
     */
    public  String getUserName(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getIssuer();
        } catch (Exception e) {
            log.error("error", e);
        }
        return username;
    }
    /**
     * 验证token 是否过期(true:已过期 false:未过期)
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            //token的过期时间 = 签发token时的时间 + 过期时长
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("error", e);
            return true;
        }
    }

    /**
     * 获取token过期的时间 分钟
     * @return
     */
    public long getRemainingTime(String token) {
        Claims claims = getClaimsFromToken(token);
        return TimeUnit.MILLISECONDS.toMinutes(claims.getExpiration().getTime() - System.currentTimeMillis());
    }
}
