package com.forum.buge_server.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class JwtConfig {
    private String secret = "abcdefg1234567";
    private long expire = 3600 * 60 * 60;
    private String header = "token";

    /**
     * 生成token
     * 
     * @param subject
     * @return
     */
    public String createToken(String subject) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);// 过期时间

        return Jwts.builder().setHeaderParam("typ", "JWT").setSubject(subject).setIssuedAt(nowDate)
                .setExpiration(expireDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 获取token中注册信息
     * 
     * @param token
     * @return
     */
    public Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token是否过期失效
     * 
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired(Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 获取token失效时间
     * 
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }

    /**
     * 获取用户id从token中
     */
    public String getUserIdFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }

}
