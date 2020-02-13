package com.tensquare.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author: Durian
 * Date: 2020/2/13 14:31
 * Description:
 */
@Data
@Component
@ConfigurationProperties("jwt.config")
public class JwtUtils
{
    private String key;
    /** 过期时间 ms */
    private long ttl;

    /**
     * 生成jwt
     *
     * @param id      id信息
     * @param subject 用户信息
     * @param roles   角色信息
     * @return jwt串
     */
    public String createJWT(String id, String subject, String roles)
    {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);
        if (ttl > 0)
        {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析jwt
     *
     * @param jwtStr jwt串
     * @return 解析内容
     */
    public Claims parseJWT(String jwtStr)
    {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}
