package xyz.onlytype.shiro.token;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author 白也
 * @date 2023/1/25 20:17
 */
@Component
@Slf4j
public class TokenUtils {
    @Value("${token.secret}")
    private String secret;

    /**
     * 生成密钥
     * @param issuer 持有人
     * @param subject 持有人id
     * @param claims 非隐私信息
     */
    public  String getToken(String issuer, String subject, Map<String, Object> claims) {
        //过期时间默认30分钟
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 30);
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setSubject(subject)
                .setIssuer(issuer)
                .setClaims(claims)
                .setExpiration(instance.getTime())
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
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ClaimJwtException e) {
            log.error("error", e);
        }
        return claims;
    }

    /**
     * 返回用户id
     * @param token token
     */
    public  String getUserId( String token){
        String userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            log.error("error", e);
        }
        return userId;
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
     * 获取token过期的时间
     */
    public DateTime getRemainingTime(String token) {
        DateTime dateTime = null;
        try {
            Claims claims = getClaimsFromToken(token);
            dateTime = DateUtil.beginOfMinute(claims.getExpiration());
        } catch (Exception e) {
            log.error("error", e);
        }
        return dateTime;
    }
}
