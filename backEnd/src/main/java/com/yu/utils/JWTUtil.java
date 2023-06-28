package com.yu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * 用于生成token和验证token
 * &#064;Author  yu
 */
@Slf4j
@Component
public class JWTUtil {

    private static String secret;

    private static long expireTime;

    private static Algorithm algorithm;


    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JWTUtil.secret = secret;
        algorithm =  Algorithm.HMAC256(secret);
    }

    @Value("${jwt.default-expires}")
    public void setExpireTime(long expireTime) {
        JWTUtil.expireTime = expireTime;
    }


    /**
     * 生成token
     * @param map 传入payload
     * @param expireTime 过期秒数
     * @return token
     */
    public static String getToken(Map map, long expireTime){
        return JWT.create().withPayload(map)
                .withExpiresAt(new Date(System.currentTimeMillis()+expireTime*1000))
                .sign(algorithm);
    }

    /**
     * 生成默认失效时间 token
     * 默认失效时间为 1周
     * @param map 传入payload
     * @return token
     */
    public static String getToken(Map map){
        return getToken(map,expireTime);
    }


    public static Map<String, Claim> verifyToken(String token){
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getClaims();
        }catch (SignatureVerificationException e){
            log.error("token被篡改");
            log.error(String.valueOf(new Date()));
            throw new RuntimeException("token被篡改");
        }catch (TokenExpiredException e){
            log.error("token过期");
            log.error(String.valueOf(new Date()));
            throw new RuntimeException("token过期");
        }
    }
}
